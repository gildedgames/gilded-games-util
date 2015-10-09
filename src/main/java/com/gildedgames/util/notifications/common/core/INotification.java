package com.gildedgames.util.notifications.common.core;

import com.gildedgames.util.io_manager.io.IO;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public interface INotification extends IO<ByteBuf, ByteBuf>
{
	/**
	 * Returns the text that will be displayed in the popup
	 */
	String getName();

	EntityPlayer getReceiver();

	EntityPlayer getSender();

	/**
	 * You can return null to just display the popup
	 * @return
	 */
	INotificationMessage getMessage();
}
