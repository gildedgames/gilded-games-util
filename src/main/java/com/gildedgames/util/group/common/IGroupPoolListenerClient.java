package com.gildedgames.util.group.common;

import java.util.UUID;

import com.gildedgames.util.group.common.core.Group;

public interface IGroupPoolListenerClient<T extends IGroupHook> extends IGroupPoolListener<T>
{
	/**
	 * Called whenever this client joins a group.
	 * @param group
	 */
	void onJoin(Group group);

	/**
	 * Called whenever this client leaves a group.
	 * @param group
	 */
	void onLeave(Group group);

	/**
	 * Called whenever this client is invited by
	 * inviter.
	 * @param group
	 * @param inviter
	 */
	void onInvited(Group group, UUID inviter);

	void onInviteRemoved(Group group);
}
