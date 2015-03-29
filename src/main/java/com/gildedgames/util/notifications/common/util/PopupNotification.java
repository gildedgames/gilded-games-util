package com.gildedgames.util.notifications.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.notifications.common.core.INotificationMessage;

public class PopupNotification extends AbstractNotification
{
	private String message;

	private PopupNotification()
	{

	}

	public PopupNotification(String message, EntityPlayer sender, EntityPlayer receiver)
	{
		super(sender, receiver);
		this.message = message;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		super.write(output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		super.read(input);
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
