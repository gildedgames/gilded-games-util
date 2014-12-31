package com.gildedgames.util.playerhook.common.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.io_manager.io.INBT;
import com.gildedgames.util.io_manager.networking.ISyncable;
import com.gildedgames.util.playerhook.common.IPlayerHookPool;

public interface IPlayerHook extends INBT, ISyncable
{
	
	IPlayerHookPool getParentPool();

	void entityInit(EntityPlayer player);
	
	IPlayerProfile getProfile();
	
	void setProfile(IPlayerProfile profile);
	
	void onUpdate();
	
	boolean onLivingAttack(DamageSource source);
	
	void onDeath();

	void onChangedDimension();

	void onRespawn();
	
}
