package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupSettings;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

public class DefaultSettings implements IGroupSettings
{

	@Override
	public boolean canPlayerJoinMultipleGroups()
	{
		return false;
	}

	@Override
	public boolean groupRemovedWhenEmpty()
	{
		return true;
	}

	@Override
	public IGroupPerms createPermissions(Group group, GroupMember creating)
	{
		return new GroupPermsDefault(group, creating);
	}

}
