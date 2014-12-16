package com.gildedgames.playerhook.common.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import com.gildedgames.playerhook.common.PlayerHookManager;
import com.gildedgames.playerhook.common.util.INBT;
import com.gildedgames.playerhook.common.util.ISyncable;

public interface IPlayerHook extends INBT, ISyncable
{
	
	PlayerHookManager getManager();

	void entityInit();
	
	PlayerProfile getProfile();
	
	void setProfile(PlayerProfile profile);
	
	void onUpdate();
	
	void onJoinWorld(EntityPlayer player);
	
	boolean onLivingAttack(DamageSource source);
	
	void onDeath();

	void onChangedDimension();

	void onRespawn();
	
}
