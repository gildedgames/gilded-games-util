package com.gildedgames.util.group.common;

import com.gildedgames.util.group.common.player.GroupMember;


public interface IGroupPerms
{

	String getName();
	
	String getDescription();
	
	boolean canBeInvited(GroupMember member);
	
	boolean canJoin(GroupMember member);
	
	boolean isVisible();
	
}
