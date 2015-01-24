package com.gildedgames.util.universe.common.util;

import net.minecraft.entity.player.EntityPlayer;

public interface IUniverseListener
{

	void onTravelToPre(IUniverse universe, EntityPlayer player, boolean hasTravelBefore);
	
	void onTravelToPost(IUniverse universe, EntityPlayer player, boolean hasTravelBefore);
	
	void onTravelFromPre(IUniverse universe, EntityPlayer player, boolean hasLeftBefore);
	
	void onTravelFromPost(IUniverse universe, EntityPlayer player, boolean hasLeftBefore);
	
}
