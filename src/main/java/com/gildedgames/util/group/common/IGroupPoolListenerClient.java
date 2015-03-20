package com.gildedgames.util.group.common;

import com.gildedgames.util.group.common.core.Group;

public interface IGroupPoolListenerClient<T extends IGroupHook> extends IGroupPoolListener<T>
{
	void onJoin(Group group);

	void onLeave(Group group);

	void onInvited(Group group);
}
