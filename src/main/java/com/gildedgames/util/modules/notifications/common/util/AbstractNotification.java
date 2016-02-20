package com.gildedgames.util.modules.notifications.common.util;

import java.util.UUID;

import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.modules.notifications.common.core.INotification;

import io.netty.buffer.ByteBuf;

public abstract class AbstractNotification implements INotification
{
	private UUID sender, receiver;

	protected AbstractNotification()
	{

	}

	public AbstractNotification(UUID sender, UUID receiver)
	{
		this.sender = sender;
		this.receiver = receiver;
	}

	@Override
	public void write(ByteBuf output)
	{
		boolean senderKnown = this.sender != null;
		output.writeBoolean(senderKnown);
		if (senderKnown)
		{
			IOUtil.writeUUID(this.sender, output);
		}
		IOUtil.writeUUID(this.receiver, output);
	}

	@Override
	public void read(ByteBuf input)
	{
		if (input.readBoolean())
		{
			this.sender = IOUtil.readUUID(input);
		}
		this.receiver = IOUtil.readUUID(input);
	}

	@Override
	public UUID getReceiver()
	{
		return this.receiver;
	}

	@Override
	public UUID getSender()
	{
		return this.sender;
	}

}
