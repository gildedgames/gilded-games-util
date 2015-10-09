package com.gildedgames.util.notifications.common.util;

import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotification;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractNotification implements INotification
{
	private EntityPlayer sender, receiver;

	protected AbstractNotification()
	{

	}

	public AbstractNotification(EntityPlayer sender, EntityPlayer receiver)
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
			IOUtil.writeUUID(this.sender.getUniqueID(), output);
		}
		IOUtil.writeUUID(this.receiver.getUniqueID(), output);
	}

	@Override
	public void read(ByteBuf input)
	{
		if (input.readBoolean())
		{
			this.sender = NotificationCore.playerFromUUID(IOUtil.readUUID(input));
		}
		this.receiver = NotificationCore.playerFromUUID(IOUtil.readUUID(input));
	}

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
