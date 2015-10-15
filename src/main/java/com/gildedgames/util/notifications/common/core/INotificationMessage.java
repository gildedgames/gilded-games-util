package com.gildedgames.util.notifications.common.core;

import java.util.List;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

import net.minecraft.entity.player.EntityPlayer;

public interface INotificationMessage extends IO<IOBridge, IOBridge>
{
	/**
	 * Returns the title that will be displayed
	 * to the user.
	 */
	String getTitle();

	/**
	 * Returns the description inside of the
	 * notification.
	 */
	String getDescription();

	EntityPlayer getReceiver();

	/**
	 * Returns the sender of this notification.
	 * Return null if the sender is not a player.
	 */
	EntityPlayer getSender();

	/**
	 * Returns the responses to this notification. 
	 * The order of this list should always remain
	 * identical.
	 */
	List<INotificationResponse> getResponses();

	/**
	 * Called whenever a player opens this message.
	 */
	void onOpened();

	void onDisposed();

	/**
	 * Returns true if this notification still makes
	 * sense given the current state of the world.
	 */
	boolean isRelevant();

	/**
	 * Return a string to compare on for syncing and
	 * for equality. If two messages have the same key
	 * but one was added earlier that one will be 
	 * removed and the new one will be put on front
	 */
	String getKey();
}
