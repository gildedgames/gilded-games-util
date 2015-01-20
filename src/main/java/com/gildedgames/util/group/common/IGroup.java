package com.gildedgames.util.group.common;

import net.minecraft.entity.player.EntityPlayer;

public interface IGroup
{
	
	boolean join(EntityPlayer player);
	
	boolean leave(EntityPlayer player);
	
}
