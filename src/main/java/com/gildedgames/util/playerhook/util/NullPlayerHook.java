package com.gildedgames.util.playerhook.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;
import com.gildedgames.util.playerhook.common.player.IPlayerProfile;

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
	public void writeToNBT(NBTTagCompound tag) {}

	@Override
	public void readFromNBT(NBTTagCompound tag) {}

	@Override
	public void writeToClient(ByteBuf buf) {}

	@Override
	public void readFromServer(ByteBuf buf) {}

	@Override
	public void writeToServer(ByteBuf buf) {}

	@Override
	public void readFromClient(ByteBuf buf) {}

	@Override
	public void markDirty() {}

	@Override
	public void markClean() {}

	@Override
	public boolean isDirty() { return false; }

	@Override
	public IPlayerHookPool getParentPool() { return NullPlayerHookPool.instance(); }

	@Override
	public void entityInit() {}

	@Override
	public IPlayerProfile getProfile() { return null; }

	@Override
	public void setProfile(IPlayerProfile profile) {}

	@Override
	public void onUpdate() {}

	@Override
	public void onJoinWorld() {}

	@Override
	public boolean onLivingAttack(DamageSource source) { return false; }

	@Override
	public void onDeath() {}

	@Override
	public void onChangedDimension() {}

	@Override
	public void onRespawn() {}

}
