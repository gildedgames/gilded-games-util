package com.gildedgames.util.notifications.common.player;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.io_manager.io.IOSyncable;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerNotification implements IPlayerHook
{

	private IPlayerProfile profile;

	private IPlayerHookPool<PlayerNotification> pool;

	private List<INotificationMessage> notifications = new ArrayList<INotificationMessage>();

	public PlayerNotification(IPlayerProfile profile, IPlayerHookPool<PlayerNotification> pool)
	{
		this.profile = profile;

		this.pool = pool;
	}

	@Override
	public void write(NBTTagCompound output)
	{
	}

	@Override
	public void read(NBTTagCompound input)
	{
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
	public void onUpdate()
	{
	}

	@Override
	public boolean onLivingAttack(DamageSource source)
	{
		return true;
	}

	@Override
	public void onDeath()
	{
	}

	@Override
	public void onChangedDimension()
	{
	}

	@Override
	public void onRespawn()
	{
	}

	@Override
	public void syncTo(ByteBuf output, IOSyncable.SyncSide to)
	{
	}

	@Override
	public void syncFrom(ByteBuf input, IOSyncable.SyncSide from)
	{

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
		return new ArrayList<INotificationMessage>(this.notifications);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void markDirty()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void markClean()
	{
		// TODO Auto-generated method stub

	}

}
