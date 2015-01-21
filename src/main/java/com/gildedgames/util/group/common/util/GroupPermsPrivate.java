package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

public class GroupPermsPrivate implements IGroupPerms
{

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

}
