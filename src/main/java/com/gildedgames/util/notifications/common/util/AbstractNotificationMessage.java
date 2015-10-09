package com.gildedgames.util.notifications.common.util;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotificationMessage;

import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractNotificationMessage implements INotificationMessage
{
	private EntityPlayer sender, receiver;

	protected AbstractNotificationMessage()
	{

	}

	public AbstractNotificationMessage(EntityPlayer sender, EntityPlayer receiver)
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
			IOUtil.setUUID(this.sender.getUniqueID(), output, "sender");
		}
		IOUtil.setUUID(this.receiver.getUniqueID(), output, "receiver");
		this.writeMessage(output);
	}

	protected abstract void writeMessage(IOBridge output);

	@Override
	public final void read(IOBridge input)
	{
		if (input.getBoolean("senderKnown"))
		{
			this.sender = NotificationCore.playerFromUUID(IOUtil.getUUID(input, "sender"));
		}
		this.receiver = NotificationCore.playerFromUUID(IOUtil.getUUID(input, "receiver"));
		this.readMessage(input);
	}

	protected abstract void readMessage(IOBridge input);

	@Override
	public EntityPlayer getReceiver()
	{
		return this.receiver;
	}

	@Override
	public EntityPlayer getSender()
	{
		return this.sender;
	}
}
