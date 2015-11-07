package com.gildedgames.util.notifications.common.util;

import java.util.UUID;

import com.gildedgames.util.notifications.common.core.INotificationMessage;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PopupNotification extends AbstractNotification
{
	private String message;

	private PopupNotification()
	{

	}

	public PopupNotification(String message, UUID sender, UUID receiver)
	{
		super(sender, receiver);
		this.message = message;
	}

	@Override
	public void write(ByteBuf output)
	{
		super.write(output);
		ByteBufUtils.writeUTF8String(output, this.message);
	}

	@Override
	public void read(ByteBuf input)
	{
		super.read(input);
		this.message = ByteBufUtils.readUTF8String(input);
	}

	@Override
	public String getName()
	{
		return this.message;
	}

	@Override
	public INotificationMessage getMessage()
	{
		return null;
	}

}
