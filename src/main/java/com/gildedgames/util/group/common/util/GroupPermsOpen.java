package com.gildedgames.util.group.common.util;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

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
	public boolean canBeInvited(GroupMember member)
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
