package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;
import net.minecraft.nbt.NBTTagCompound;

public class GroupPermsPrivate implements IGroupPerms
{
	
	public GroupPermsPrivate()
	{
		
	}

	@Override
	public String getName()
	{
		return "group.private.name";
	}

	@Override
	public String getDescription()
	{
		return "group.private.description";
	}

	@Override
	public boolean canBeInvited(GroupMember inviter, GroupMember member)
	{
		return true;
	}

	@Override
	public boolean canJoin(GroupMember member)
	{
		return false;
	}

	@Override
	public boolean isVisible()
	{
		return false;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		
	}

	@Override
	public void read(NBTTagCompound input)
	{
		
	}

}
