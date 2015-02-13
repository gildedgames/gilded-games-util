package com.gildedgames.util.player.common.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.networking.ISyncable;
import com.gildedgames.util.player.common.IPlayerHookPool;

public interface IPlayerHook extends NBT, ISyncable
{

	@SuppressWarnings("rawtypes")
	IPlayerHookPool getParentPool();//Why are self referential type parameters not possible ;__;

	void entityInit(EntityPlayer player);

	IPlayerProfile getProfile();

	//void setProfile(IPlayerProfile profile);

	void onUpdate();

	boolean onLivingAttack(DamageSource source);

	void onDeath();

	void onChangedDimension();

	void onRespawn();

}
