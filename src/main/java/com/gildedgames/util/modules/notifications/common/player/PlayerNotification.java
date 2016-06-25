package com.gildedgames.util.modules.notifications.common.player;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotification
{
	private List<INotificationMessage> notifications = new ArrayList<>();

	private EntityPlayer player;

	public PlayerNotification(EntityPlayer player)
	{
		this.player = player;
	}

	public void addNotification(INotificationMessage notification)
	{
		INotificationMessage old = this.getFromKey(notification.getKey());
		if (old != null)
		{
			this.removeNotification(old);
		}
		this.notifications.add(notification);
	}

	public void removeNotification(INotificationMessage notification)
	{
		this.notifications.remove(notification);
	}

	public List<INotificationMessage> getNotifications()
	{
		return new ArrayList<>(this.notifications);
	}

	public INotificationMessage getFromKey(String key)
	{
		for (INotificationMessage message : this.notifications)
		{
			if (message.getKey().equals(key))
			{
				return message;
			}
		}
		return null;
	}

	public EntityPlayer getPlayer()
	{
		return this.player;
	}

	public static class Storage implements Capability.IStorage<PlayerNotification>
	{
		@Override
		public NBTBase writeNBT(Capability<PlayerNotification> capability, PlayerNotification instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			NBTHelper.setIOList("notifications", instance.notifications, compound);

			return compound;
		}

		@Override
		public void readNBT(Capability<PlayerNotification> capability, PlayerNotification instance, EnumFacing side, NBTBase nbt)
		{
			if (!(nbt instanceof NBTTagCompound))
			{
				return;
			}

			instance.notifications = NBTHelper.getIOList("notifications", (NBTTagCompound) nbt);
		}
	}
}
