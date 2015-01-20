package com.gildedgames.util.player.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class NullPlayerHook implements IPlayerHook
{

	private static NullPlayerHook INSTANCE;

	private NullPlayerHook()
	{

	}

	public static NullPlayerHook instance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new NullPlayerHook();
		}

		return INSTANCE;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
	}

	@Override
	public void read(NBTTagCompound tag)
	{
	}

	@Override
	public void writeToClient(ByteBuf buf)
	{
	}

	@Override
	public void readFromServer(ByteBuf buf)
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
	public void markDirty()
	{
	}

	@Override
	public void markClean()
	{
	}

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public IPlayerHookPool<NullPlayerHook> getParentPool()
	{
		return NullPlayerHookPool.instance();
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return null;
	}

	@Override
	public void setProfile(IPlayerProfile profile)
	{
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public boolean onLivingAttack(DamageSource source)
	{
		return false;
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
