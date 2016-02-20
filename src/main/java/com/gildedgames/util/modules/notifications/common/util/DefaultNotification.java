package com.gildedgames.util.modules.notifications.common.util;

import java.util.UUID;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.modules.notifications.common.core.INotification;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;

import io.netty.buffer.ByteBuf;

public class DefaultNotification implements INotification
{
	private INotificationMessage message;

	private DefaultNotification()
	{

	}

	public DefaultNotification(INotificationMessage message)
	{
		this.message = message;
	}

	@Override
	public void write(ByteBuf output)
	{
		(new ByteBufBridge(output)).setIO("message", this.message);
	}

	@Override
	public void read(ByteBuf input)
	{
		this.message = (new ByteBufBridge(input)).getIO("message");
	}

	@Override
	public String getName()
	{
		return this.message.getTitle();
	}

	@Override
	public UUID getReceiver()
	{
		return this.message.getReceiver();
	}

	@Override
	public UUID getSender()
	{
		return this.message.getSender();
	}

	@Override
	public INotificationMessage getMessage()
	{
		return this.message;
	}

}
