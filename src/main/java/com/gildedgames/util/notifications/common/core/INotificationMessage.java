package com.gildedgames.util.notifications.common.core;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBT;

public interface INotificationMessage extends NBT
{
	String getTitle();

	String getDescription();

	EntityPlayer getReceiver();

	EntityPlayer getSender();

	List<INotificationResponse> getResponses();

	void onOpened();

	void onDisposed();

	boolean isRelevant();

	/**
	 * Return a string to compare on for syncing and
	 * for equality. If two messages have the same key
	 * but one was added earlier that one will be 
	 * removed and the new one will be put on front
	 * @return
	 */
	String getKey();
}
