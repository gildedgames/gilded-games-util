package com.gildedgames.util.notifications.player;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerNotifications implements IPlayerHook
{

	private IPlayerProfile profile;

	private IPlayerHookPool<PlayerNotifications> pool;

	public PlayerNotifications(IPlayerProfile profile, IPlayerHookPool<PlayerNotifications> pool)
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

	@Override
	public void writeToClient(ByteBuf buf)
	{
	}

	@Override
	public void writeToServer(ByteBuf buf)
	{
	}

	@Override
	public void readFromClient(ByteBuf buf)
	{
	}

	@Override
	public void readFromServer(ByteBuf buf)
	{
	}

	@Override
	public IPlayerHookPool<PlayerNotifications> getParentPool()
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

}
