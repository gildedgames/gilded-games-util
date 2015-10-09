package com.gildedgames.util.notifications.common.util;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.notifications.common.core.INotificationMessage;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageNotification extends AbstractNotification
{

	private String title;

	private final DefaultMessage message;

	private MessageNotification()
	{
		this.message = new DefaultMessage("", "", null, null);
	}

	public MessageNotification(String title, String message, EntityPlayer sender, EntityPlayer receiver)
	{
		super(sender, receiver);
		this.title = title;
		this.message = new DefaultMessage(title, message, sender, receiver);
	}

	@Override
	public void read(ByteBuf input)
	{
		super.read(input);
		this.message.read(new ByteBufBridge(input));
	}

	@Override
	public void write(ByteBuf output)
	{
		super.write(output);
		this.message.write(new ByteBufBridge(output));
	}

	@Override
	public String getName()
	{
		return this.title;
	}

	@Override
	public INotificationMessage getMessage()
	{
		return this.message;
	}

}
