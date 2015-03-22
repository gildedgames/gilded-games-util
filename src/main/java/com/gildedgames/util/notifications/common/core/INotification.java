package com.gildedgames.util.notifications.common.core;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBT;

public interface INotification extends NBT
{
	String getName();

	EntityPlayer getReceiver();

	EntityPlayer getSender();

	/**
	 * You can return null to just display the popup
	 * @return
	 */
	INotificationMessage getMessage();
}
