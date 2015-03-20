package com.gildedgames.util.group.common.permissions;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;

public class GroupPermsClosed implements IGroupPerms
{

	public GroupPermsClosed()
	{

	}

	@Override
	public String getName()
	{
		return "group.closed.name";
	}

	@Override
	public String getDescription()
	{
		return "group.closed.description";
	}

	@Override
	public boolean canInvite(Group group, GroupMember inviter, GroupMember member)
	{
		return true;
	}

	@Override
	public boolean canJoin(Group group, GroupMember member)
	{
		return false;
	}

	@Override
	public boolean isVisible(Group group)
	{
		return true;
	}

	@Override
	public void write(NBTTagCompound output)
	{

	}

	@Override
	public void read(NBTTagCompound input)
	{

	}

	@Override
	public boolean canChangeOwner(Group group, GroupMember newOwner, GroupMember changing)
	{
		return changing.equals(group.getOwner());
	}

	@Override
	public boolean doAutoReassignOwner(Group group)
	{
		return true;
	}

	@Override
	public boolean canRemoveGroup(Group group, GroupMember remover)
	{
		return remover.equals(group.getOwner());
	}

	@Override
	public boolean canRemoveMember(Group group, GroupMember toRemove, GroupMember remover)
	{
		return remover.equals(group.getOwner());
	}

	@Override
	public GroupMember chooseNewOwner(Group group)
	{
		return group.getMemberData().getMembers().get(group.getMemberData().size());
	}

}
