package com.gildedgames.util.notifications.core;

import net.minecraft.entity.player.EntityPlayer;

public interface NotificationDispatcher
{
	void sendNotification(INotification notification, EntityPlayer player);
}
