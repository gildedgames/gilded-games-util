package com.gildedgames.util.player.common;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.player.PlayerCore;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHook;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookClient;
import com.gildedgames.util.player.common.player.IPlayerHook;
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

public class PlayerEventHandler
{

	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			for (IPlayerHookPool<?> manager : PlayerCore.locate().getPools())
			{
				manager.get(player).getProfile().entityInit(player);
				manager.get(player).entityInit(player);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			for (IPlayerHookPool<?> manager : PlayerCore.locate().getPools())
			{
				IPlayerHook playerHook = manager.get(player);

				if (playerHook.getProfile().getEntity() == null)
				{
					playerHook.getProfile().setEntity(player);
				}

				if (event.entity.worldObj.isRemote)
				{
					this.refreshServer(playerHook);
				}
				else
				{
					this.refreshClients(playerHook);
				}
			}
		}
	}

	public void refreshServer(IPlayerHook playerHook)
	{
		if (playerHook.isDirty())
		{
			UtilCore.NETWORK.sendToServer(new MessagePlayerHookClient(playerHook));
			playerHook.markClean();
		}
	}

	public void refreshClients(IPlayerHook playerHook)
	{
		if (playerHook.isDirty())
		{
			UtilCore.NETWORK.sendToAll(new MessagePlayerHook(playerHook));
			playerHook.markClean();
		}
	}

	@SubscribeEvent
	public void onLoggedIn(PlayerLoggedInEvent event)
	{
		for (IPlayerHookPool<?> manager : PlayerCore.locate().getPools())
		{
			IPlayerHook playerHook = manager.get(event.player);

			playerHook.getProfile().setLoggedIn(true);

			UtilCore.NETWORK.sendToAll(new MessagePlayerHook(playerHook));
		}
	}

	@SubscribeEvent
	public void onLoggedOut(PlayerLoggedOutEvent event)
	{
		for (IPlayerHookPool<?> manager : PlayerCore.locate().getPools())
		{
			IPlayerHook playerHook = manager.get(event.player);

			playerHook.getProfile().setEntity(null);
			playerHook.getProfile().setLoggedIn(false);

			UtilCore.NETWORK.sendToAll(new MessagePlayerHook(playerHook));
		}
	}

}
