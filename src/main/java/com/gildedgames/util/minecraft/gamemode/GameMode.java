package com.gildedgames.util.minecraft.gamemode;

import net.minecraft.entity.player.PlayerCapabilities;

public interface GameMode
{

	String getName();
	
	String getID();
	
	String getDescriptionLine1();
	
	String getDescriptionLine2();
	
	void configurePlayerCapabilities(PlayerCapabilities capabilities);
	
}
