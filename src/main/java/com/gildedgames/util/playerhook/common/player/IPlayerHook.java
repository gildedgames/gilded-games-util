package com.gildedgames.util.playerhook.common.player;

import net.minecraft.util.DamageSource;

import com.gildedgames.util.iomanager.INBT;
import com.gildedgames.util.iomanager.ISyncable;
import com.gildedgames.util.playerhook.common.IPlayerHookPool;

public interface IPlayerHook extends INBT, ISyncable
{
	
	IPlayerHookPool getParentPool();

	void entityInit();
	
	IPlayerProfile getProfile();
	
	void setProfile(IPlayerProfile profile);
	
	void onUpdate();
	
	void onJoinWorld();
	
	boolean onLivingAttack(DamageSource source);
	
	void onDeath();

	void onChangedDimension();

	void onRespawn();
	
}
