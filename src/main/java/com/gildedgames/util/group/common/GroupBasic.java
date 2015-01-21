package com.gildedgames.util.group.common;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.group.common.player.GroupMember;

public class GroupBasic implements IGroup
{

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

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public void setName(String name)
	{
		
	}

	@Override
	public GroupPermissions getPermissions()
	{
		return null;
	}

	@Override
	public void setPermissions(GroupPermissions permissions)
	{
		
	}

	@Override
	public boolean join(EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean leave(EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean invite(EntityPlayer player)
	{
		return false;
	}

	@Override
	public GroupMember getOwner()
	{
		return null;
	}

	@Override
	public List<GroupMember> getMembers()
	{
		return null;
	}

	@Override
	public List<GroupMember> getMembersOnline()
	{
		return null;
	}

	@Override
	public List<GroupMember> getMembersOffline()
	{
		return null;
	}

	@Override
	public IGroupPool getParentPool()
	{
		return null;
	}

}
