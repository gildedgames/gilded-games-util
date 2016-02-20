package com.gildedgames.util.modules.group.common;

import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.core.GroupInfo;

public interface IGroupPoolListener<T extends IGroupHook>
{
	/**
	 * Creates listeners for specific hooks.
	 * Returns null if no listeners for that 
	 * group are needed.
	 * @param group
	 * @return
	 */
	T createGroupHook(Group group);

	/**
	 * Called whenever a group is added to the pool
	 * @param group
	 */
	void onGroupAdded(Group group);

	/**
	 * Called whenever a group is removed from the pool
	 * @param group
	 */
	void onGroupRemoved(Group group);

	/**
	 * Called when the info of a group changes, such
	 * as its name or its permissions
	 * @param group
	 * @param infoOld
	 * @param infoNew
	 */
	void onGroupInfoChanged(Group group, GroupInfo infoOld, GroupInfo infoNew);
}
