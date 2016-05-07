package com.gildedgames.util.modules.instances;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBT;

public interface Instance extends NBT
{
	
	void onJoin(EntityPlayer player);

	void onLeave(EntityPlayer player);

	List<EntityPlayer> getPlayers();
	
	int getDimIdInside();
	
}
