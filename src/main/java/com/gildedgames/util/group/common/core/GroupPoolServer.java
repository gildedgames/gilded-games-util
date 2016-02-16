package com.gildedgames.util.group.common.core;

import java.util.List;
import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.group.common.IGroupSettings;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class GroupPoolServer extends GroupPool
{

	private IGroupSettings settings;

	public GroupPoolServer(String id, IGroupSettings settings)
	{
		super(id);
		this.settings = settings;
	}

	@Override
	public Group create(String name, EntityPlayer creating, IGroupPerms perms)
	{
		if (!this.settings.duplicateNamesAllowed())
		{
			for (Group group : this.groups.values())
			{
				if (group.getName().equals(name))
				{
					UtilModule.print("Tried to add group " + name + " but it already existed");
					return null;
				}
			}
		}

		Group group = new Group(this);

		GroupInfo groupInfo = new GroupInfo(UUID.randomUUID(), name, perms);
		group.setGroupInfo(groupInfo);

		MemberData memberData = new MemberData();
		memberData.setHooks(this.createHooks(group));
		group.setMemberData(memberData);

		this.addGroupDirectly(group);

		UtilModule.NETWORK.sendToAll(new PacketAddGroup(this, groupInfo));

		this.addMember(creating.getGameProfile().getId(), group);

		return group;
	}

	@Override
	public void addMember(UUID player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		if (group.getPermissions().canJoin(group, player))//Maybe put this check in PacketAddMember/
		{
			GroupMember member = GroupMember.get(player);
			this.leaveOldGroup(member);
			UtilModule.NETWORK.sendToGroup(new PacketAddMember(this, group, member), group);
			UtilModule.NETWORK.sendTo(new PacketJoin(this, group), (EntityPlayerMP) member.getProfile().getEntity());
			this.addMemberDirectly(group, member);
		}
	}

	@Override
	public void removeMember(UUID player, Group group)
	{
		if (!this.assertValidGroup(group) || !this.assertMemberIn(group, player))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilModule.NETWORK.sendToGroup(new PacketRemoveMember(this, group, member), group);
		this.removeMemberDirectly(group, member);

		group.getPermissions().onMemberRemoved(group, player);

		if (this.settings.groupRemovedWhenEmpty() && group.getMemberData().size() == 0 && this.groups.containsKey(group.getUUID()))
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
		UtilModule.NETWORK.sendToAll(new PacketRemoveGroup(this, group));
	}

	@Override
	public void invite(UUID player, UUID inviter, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		GroupMember inviterG = GroupMember.get(inviter);
		if (!group.getPermissions().canInvite(group, member, inviterG))
		{
			UtilModule.print("Player " + inviterG.getProfile().getUsername() + " tried to invite " + member.getProfile().getUsername() + " but did not have the permissions.");
			return;
		}
		this.inviteDirectly(group, member, inviterG);
		UtilModule.NETWORK.sendToGroup(new PacketAddInvite(this, group, member, inviterG), group);
		UtilModule.NETWORK.sendTo(new PacketInvite(this, group, inviterG), (EntityPlayerMP) member.getProfile().getEntity());
	}

	@Override
	public void removeInvitation(UUID player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		this.removeInvitationDirectly(group, member);
		UtilModule.NETWORK.sendToGroup(new PacketRemoveInvite(this, group, member), group);
		UtilModule.NETWORK.sendTo(new PacketRemoveInvitation(this, group, member), (EntityPlayerMP) member.getProfile().getEntity());
	}

	@Override
	public void changeGroupInfo(UUID changing, Group group, GroupInfo newInfo)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		if (!group.getPermissions().canEditGroupInfo(group, changing))
		{
			return;
		}
		GroupInfo finalInfo = new GroupInfo(group.getUUID(), newInfo.getName(), newInfo.getPermissions());
		this.changeGroupInfoDirectly(group, finalInfo);
		UtilModule.NETWORK.sendToAll(new PacketChangeGroupInfo(changing, group, finalInfo));
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
				this.removeMember(member.getProfile().getUUID(), oldGroups.get(0));
			}
		}
	}

}
