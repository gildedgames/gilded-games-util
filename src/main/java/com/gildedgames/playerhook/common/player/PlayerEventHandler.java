package com.gildedgames.playerhook.common.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerInfo;

public class PlayerEventHandler
{
	
	private int tickCounter;

	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			PlayerHook.get(player).onJoinWorld(player);
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			if (!PlayerHook.get((EntityPlayer) event.entity).onLivingAttack(event.source))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			PlayerHook hook = PlayerHook.get(player);
			
			if (hook.getEntity() == null)
			{
				hook.setEntity(player);
			}
			
			hook.onUpdate();
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			PlayerHook.get(player).onDeath();
		}
	}
	
	@SubscribeEvent
	public void onLoggedIn(PlayerLoggedInEvent event)
	{
		PlayerHook playerHook = PlayerHook.get(event.player);

		playerHook.setLoggedIn(true);
		
		PlayerHookCore.NETWORK.sendToAll(new MessagePlayerInfo(playerHook));
	}
	
	@SubscribeEvent
	public void onLoggedOut(PlayerLoggedOutEvent event)
	{
		PlayerHook playerHook = PlayerHook.get(event.player);

		playerHook.setEntity(null);
		playerHook.setLoggedIn(false);

		PlayerHookCore.NETWORK.sendToAll(new MessagePlayerInfo(playerHook));
	}
	
	@SubscribeEvent
	public void onChangedDimension(PlayerChangedDimensionEvent event)
	{
		PlayerHook playerHook = PlayerHook.get(event.player);
		
		playerHook.onChangedDimension();
	}
	
	@SubscribeEvent
	public void onRespawn(PlayerRespawnEvent event)
	{
		PlayerHook playerHook = PlayerHook.get(event.player);
		
		playerHook.onRespawn();
	}
	
	@SubscribeEvent
	public void onTick(ServerTickEvent event)
	{
		if (event.side == Side.SERVER)
		{
			if (event.phase == Phase.START)
			{
				tickCounter++;
				
				if (minutesHasPassed(3))
				{
					PlayerHookCore.flushDataOut();
				}
			}
		}
	}

	public boolean minutesHasPassed(int minutes)
	{
		return this.tickCounter % (1200 * minutes) == 0;
	}
	
}
