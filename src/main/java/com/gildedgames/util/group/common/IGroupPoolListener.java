package com.gildedgames.util.group.common;

import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;

public interface IGroupPoolListener<T extends IGroupHook>
{
	T createGroupHook(Group group);

	void onGroupAdded(Group group);

	void onGroupRemoved(Group group);

	void onGroupInfoChanged(Group group, GroupInfo info);
}
