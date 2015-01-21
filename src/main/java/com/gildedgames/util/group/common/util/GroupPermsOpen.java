package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

public class GroupPermsOpen implements IGroupPerms
{

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

}
