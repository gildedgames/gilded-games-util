package com.gildedgames.util.notifications.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotification;

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
	public void write(NBTTagCompound output)
	{
		boolean senderKnown = this.sender != null;
		output.setBoolean("senderKnown", senderKnown);
		if (senderKnown)
		{
			IOUtil.setUUID(this.sender.getUniqueID(), output, "sender");
		}
		IOUtil.setUUID(this.receiver.getUniqueID(), output, "receiver");
	}

	@Override
	public void read(NBTTagCompound input)
	{
		if (input.getBoolean("senderKnown"))
		{
			this.sender = NotificationCore.playerFromUUID(IOUtil.getUUID(input, "sender"));
		}
		this.receiver = NotificationCore.playerFromUUID(IOUtil.getUUID(input, "receiver"));
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
