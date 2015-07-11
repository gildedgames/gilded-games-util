package com.gildedgames.util.minecraft.gamemode;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.gildedgames.util.core.UtilCore;

public class GameModeTracker
{

	@SubscribeEvent
	public void onLivingEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)event.entity;
			
			GameMode mode = UtilCore.locate().getGameModeInjector().convertToGameMode(player.theItemInWorldManager.getGameType());
			
			if (mode != null)
			{
				mode.configurePlayerCapabilities(player.capabilities);
			}
		}
	}
	
}
