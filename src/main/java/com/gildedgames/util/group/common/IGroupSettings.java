package com.gildedgames.util.group.common;

import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

public interface IGroupSettings
{
	boolean canPlayerJoinMultipleGroups();

	boolean groupRemovedWhenEmpty();

	IGroupPerms createPermissions(Group group, GroupMember creating);
}
