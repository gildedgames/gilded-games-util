package com.gildedgames.util.modules.notifications.common.player;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotification extends EntityHook<EntityPlayer>
{
	private List<INotificationMessage> notifications = new ArrayList<>();

	@Override
	public void init(Entity entity, World world)
	{
		super.init(entity, world);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTHelper.setIOList("notifications", this.notifications, compound);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		this.notifications = NBTHelper.getIOList("notifications", compound);
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

	public static class Factory implements IEntityHookFactory<PlayerNotification>
	{
		@Override
		public PlayerNotification createHook()
		{
			return new PlayerNotification();
		}

		@Override
		public void writeFull(ByteBuf buf, PlayerNotification hook) { }

		@Override
		public void readFull(ByteBuf buf, PlayerNotification hook) { }
	}
}
