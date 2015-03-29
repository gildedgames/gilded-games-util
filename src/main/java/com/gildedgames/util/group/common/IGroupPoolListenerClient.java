package com.gildedgames.util.group.common;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.group.common.core.Group;

public interface IGroupPoolListenerClient<T extends IGroupHook> extends IGroupPoolListener<T>
{
	void onJoin(Group group);

	void onLeave(Group group);

	void onInvited(Group group, EntityPlayer inviter);
}
