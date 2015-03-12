package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;
import net.minecraft.nbt.NBTTagCompound;

public class GroupPermsOpen implements IGroupPerms
{
	
	public GroupPermsOpen()
	{
		
	}

	@Override
	public String getName()
	{
		return "group.open.name";
	}

	@Override
	public String getDescription()
	{
		return "group.open.description";
	}

	@Override
	public boolean canBeInvited(GroupMember inviter, GroupMember member)
	{
		return true;
	}

	@Override
	public boolean canJoin(GroupMember member)
	{
		return true;
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
