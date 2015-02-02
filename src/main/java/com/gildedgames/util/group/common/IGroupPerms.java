package com.gildedgames.util.group.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.io.IRawData;


public interface IGroupPerms extends NBT, IRawData
{

	String getName();
	
	String getDescription();
	
	boolean canBeInvited(GroupMember member);
	
	boolean canJoin(GroupMember member);
	
	boolean isVisible();
	
}
