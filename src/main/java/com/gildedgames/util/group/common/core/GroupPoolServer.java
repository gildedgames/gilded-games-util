package com.gildedgames.util.group.common.core;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupSettings;
import com.gildedgames.util.group.common.player.GroupMember;

public class GroupPoolServer extends GroupPool
{

	private IGroupSettings settings;

	public GroupPoolServer(String id, IGroupSettings settings)
	{
		super(id);
		this.settings = settings;
	}

	@Override
	public Group create(String name, EntityPlayer creating)
	{
		for (Group group : this.groups)
		{
			if (group.getName().equals(name))
			{
				UtilCore.print("Tried to add group " + name + " but it already existed");
				return null;
			}
		}

		GroupMember groupMember = GroupMember.get(creating);

		Group group = new Group(this);

		GroupInfo groupInfo = new GroupInfo(name, this.settings.createPermissions(group, groupMember));
		group.setGroupInfo(groupInfo);

		MemberData memberData = new MemberData();
		memberData.setHooks(this.createHooks(group));
		group.setMemberData(memberData);

		UtilCore.NETWORK.sendToAll(new PacketAddGroup(this, groupInfo));

		this.addMember(creating, group);

		return group;
	}

	@Override
	public void addMember(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		if (group.getPermissions().canJoin(member))//Maybe put this check in PacketAddMember/
		{
			this.leaveOldGroup(member);
			UtilCore.NETWORK.sendToGroup(new PacketAddMember(this, group, member), group);
			UtilCore.NETWORK.sendTo(new PacketJoin(this, group), (EntityPlayerMP) player);
			this.addMemberDirectly(group, member);
		}
	}

	@Override
	public void removeMember(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group) || !this.assertMemberIn(group, player))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		this.removeMemberDirectly(group, member);
		UtilCore.NETWORK.sendToGroup(new PacketRemoveMember(this, group, member), group);

		if (this.settings.groupRemovedWhenEmpty() && group.getMemberData().size() == 0)
		{
			this.remove(group);
		}
	}

	@Override
	public void remove(Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		this.removeGroupDirectly(group);
		UtilCore.NETWORK.sendToAll(new PacketRemoveGroup(this, group));
	}

	@Override
	public void invite(EntityPlayer player, EntityPlayer inviter, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		GroupMember inviterG = GroupMember.get(inviter);
		if (!group.getPermissions().canInvite(member, inviterG))
		{
			UtilCore.print("Player " + player.getCommandSenderName() + " tried to invite " + member.getProfile().getUsername() + " but did not have the permissions.");
			return;
		}
		this.inviteDirectly(group, member, inviterG);
		UtilCore.NETWORK.sendToGroup(new PacketAddInvite(this, group, member, inviterG), group);
		UtilCore.NETWORK.sendTo(new PacketInvite(this, group, inviterG), (EntityPlayerMP) player);
	}

	@Override
	public void removeInvitation(EntityPlayer player, Group group)
	{

	}

	/**
	 * Called whenever a member joins a new group.
	 * If the settings don't allow joining multiple groups, leave the old one.
	 */
	private void leaveOldGroup(GroupMember member)
	{
		if (!this.settings.canPlayerJoinMultipleGroups())
		{
			List<Group> oldGroups = member.groupsInFor(this);
			if (!oldGroups.isEmpty())
			{
				this.removeMember(member.getProfile().getEntity(), oldGroups.get(0));
			}
		}
	}

}
