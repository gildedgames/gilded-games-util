package com.gildedgames.util.group.common.core;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupSettings;
import com.gildedgames.util.group.common.permissions.GroupPermsOpen;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
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

		GroupMember groupMember = GroupCore.getGroupMember(creating);
		Group group = new Group(this, new GroupPermsOpen(), name, groupMember);
		MemberData memberData = new MemberData();
		memberData.setHooks(this.createHooks(group));
		group.setMemberData(memberData);

		UtilCore.NETWORK.sendToAll(new PacketAddGroup(this, name, creating));
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
		GroupMember member = GroupCore.getGroupMember(player);
		if (group.getPermissions().canJoin(group, member))//Maybe put this check in PacketAddMember/
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
		GroupMember member = GroupCore.getGroupMember(player);
		if (group.getOwner().equals(member))
		{
			this.reassignOwner(group);
		}
		this.removeMemberDirectly(group, member);
		UtilCore.NETWORK.sendToGroup(new PacketRemoveMember(this, group, member), group);
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
	public void invite(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupCore.getGroupMember(player);
		this.inviteDirectly(group, member);
		UtilCore.NETWORK.sendToGroup(new PacketAddInvite(this, group, member), group);
	}

	@Override
	public void removeInvitation(EntityPlayer player, Group group)
	{

	}

	@Override
	public void changeOwner(EntityPlayer newOwner, Group group)
	{
		if (!this.assertValidGroup(group) || !this.assertMemberIn(group, newOwner))
		{
			return;
		}
		GroupMember newOwnerM = GroupCore.getGroupMember(newOwner);
		this.changeGroupInfo(group, group.getPermissions(), group.getName(), newOwnerM);
	}

	private void changeGroupInfo(Group group, IGroupPerms newPermissions, String newName, GroupMember newOwner)
	{
		GroupInfo newInfo = new GroupInfo(newPermissions, newName, newOwner);
		this.changeGroupInfoDirectly(group, newInfo);
		UtilCore.NETWORK.sendToAll(new PacketChangeGroupInfo(this, group, newInfo));
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

	private void reassignOwner(Group group)
	{
		if (!group.getMemberData().contains(group.getOwner()))
		{
			throw new IllegalStateException("Don't remove owner before reassigning owner");
		}

		if (this.settings.groupRemovedWhenEmpty() && group.getMemberData().size() == 1)
		{
			this.remove(group);
		}
		else if (group.getPermissions().doAutoReassignOwner(group))
		{
			GroupMember newOwner = group.getPermissions().chooseNewOwner(group);
			this.changeOwner(newOwner.getProfile().getEntity(), group);
		}
		else
		{
			this.remove(group);
		}
	}
}
