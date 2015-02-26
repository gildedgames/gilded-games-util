package com.gildedgames.util.group.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.group.common.player.GroupMember;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface IGroup extends NBT
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
