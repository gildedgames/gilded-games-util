package com.gildedgames.util.group.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.group.common.player.GroupMember;


public interface IGroupPerms extends NBT
{

	String getName();
	
	String getDescription();
	
	boolean canBeInvited(GroupMember inviter, GroupMember member);
	
	boolean canJoin(GroupMember member);
	
	boolean isVisible();
	
}
