package com.gildedgames.util.notifications.common.core;

import java.util.UUID;

import com.gildedgames.util.io_manager.io.IO;

import io.netty.buffer.ByteBuf;

public interface INotification extends IO<ByteBuf, ByteBuf>
{
	/**
	 * Returns the text that will be displayed in the popup
	 */
	String getName();

	UUID getReceiver();

	UUID getSender();

	/**
	 * You can return null to just display the popup
	 * @return
	 */
	INotificationMessage getMessage();
}
