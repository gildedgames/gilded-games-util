package com.gildedgames.util.group.common.util;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.IOManager;

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

	@Override
	public void writeRawData(ByteBuf output) throws IOException
	{
		
	}

	@Override
	public void readRawData(ByteBuf input) throws IOException
	{
		
	}

}
