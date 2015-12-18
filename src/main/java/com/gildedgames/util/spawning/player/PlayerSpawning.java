package com.gildedgames.util.spawning.player;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerSpawning implements IPlayerHook
{
	private IPlayerProfile playerProfile;

	private IPlayerHookPool<PlayerSpawning> pool;

	public PlayerSpawning(IPlayerProfile playerProfile, IPlayerHookPool<PlayerSpawning> pool)
	{
		this.playerProfile = playerProfile;

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
	public IPlayerHookPool getParentPool()
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
		return this.playerProfile;
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
	public void syncTo(ByteBuf output, SyncSide to)
	{
		
	}

	@Override
	public void syncFrom(ByteBuf input, SyncSide from)
	{
		
	}

}
