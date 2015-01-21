package com.gildedgames.util.group.common;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.io.IRawData;
import com.gildedgames.util.io_manager.io.NBT;

public interface IGroup extends NBT, IRawData
{
	
	String getName();
	
	void setName(String name);
	
	IGroupPerms getPermissions();
	
	void setPermissions(IGroupPerms permissions);
	
	boolean join(EntityPlayer player);
	
	boolean leave(EntityPlayer player);
	
	boolean invite(EntityPlayer player);
	
	GroupMember getOwner();
	
	List<GroupMember> getMembers();
	
	List<GroupMember> getMembersOnline();
	
	List<GroupMember> getMembersOffline();
	
	IGroupPool getParentPool();
	
}
