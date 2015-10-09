package com.gildedgames.util.group.common;

import com.gildedgames.util.group.common.core.Group;

import net.minecraft.entity.player.EntityPlayer;

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
	void onInvited(Group group, EntityPlayer inviter);

	void onInviteRemoved(Group group);
}
