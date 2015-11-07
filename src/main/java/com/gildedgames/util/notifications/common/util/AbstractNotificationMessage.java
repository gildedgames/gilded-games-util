package com.gildedgames.util.notifications.common.util;

import java.util.UUID;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.common.core.INotificationMessage;

public abstract class AbstractNotificationMessage implements INotificationMessage
{
	private UUID sender, receiver;

	protected AbstractNotificationMessage()
	{

	}

	public AbstractNotificationMessage(UUID sender, UUID receiver)
	{
		this.sender = sender;
		this.receiver = receiver;
	}

	@Override
	public final void write(IOBridge output)
	{
		boolean senderKnown = this.sender != null;
		output.setBoolean("senderKnown", senderKnown);
		if (senderKnown)
		{
			IOUtil.setUUID(this.sender, output, "sender");
		}
		IOUtil.setUUID(this.receiver, output, "receiver");
		this.writeMessage(output);
	}

	protected abstract void writeMessage(IOBridge output);

	@Override
	public final void read(IOBridge input)
	{
		if (input.getBoolean("senderKnown"))
		{
			this.sender = IOUtil.getUUID(input, "sender");
		}
		this.receiver = IOUtil.getUUID(input, "receiver");
		this.readMessage(input);
	}

	protected abstract void readMessage(IOBridge input);

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
