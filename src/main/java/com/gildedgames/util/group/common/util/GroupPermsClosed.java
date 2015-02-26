package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;
import net.minecraft.nbt.NBTTagCompound;

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
	public boolean canBeInvited(GroupMember member)
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
	
}
