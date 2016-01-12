package com.gildedgames.util.notifications.common.player;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.io_manager.io.IOSyncable;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerNotification implements IPlayerHook
{

	private IPlayerProfile profile;

	private IPlayerHookPool<PlayerNotification> pool;

	private List<INotificationMessage> notifications = new ArrayList<>();

	public PlayerNotification(IPlayerProfile profile, IPlayerHookPool<PlayerNotification> pool)
	{
		this.profile = profile;

		this.pool = pool;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTHelper.setIOList("notifications", this.notifications, output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.notifications = NBTHelper.getIOList("notifications", input);
	}

	@Override
	public IPlayerHookPool<PlayerNotification> getParentPool()
	{
		return this.pool;
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.profile;
	}

	@Override
	public void syncTo(ByteBuf output, IOSyncable.SyncSide to)
	{
		if (to == SyncSide.CLIENT)
		{
			IOUtil.writeIOList(this.notifications, output);
		}
	}

	@Override
	public void syncFrom(ByteBuf input, IOSyncable.SyncSide from)
	{
		if (from == SyncSide.SERVER)
		{
			this.notifications = IOUtil.readIOList(input);
		}
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

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public void markClean()
	{
	}

}
