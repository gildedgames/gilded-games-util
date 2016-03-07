package com.gildedgames.util.modules.entityhook.impl;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.entityhook.api.IEntityHookProvider;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.network.PacketSyncHook;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHookEventHandler
{
	private static final EntityHookWorldAccess worldAccess = new EntityHookWorldAccess();

	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event)
	{
		Entity oldEntity = event.original, newEntity = event.entity;

		for (PlayerHookProvider provider : EntityHookModule.impl().getPlayerHookProviders())
		{
			if (provider.isAttachedToEntity(oldEntity) && provider.canAttachToEntity(newEntity))
			{
				NBTTagCompound saveState = new NBTTagCompound();

				EntityHook oldHook = provider.getHook(oldEntity);
				oldHook.saveNBTData(saveState);

				EntityHook newHook = provider.getFactory().createHook();
				newHook.loadNBTData(saveState);
			}
		}
	}

	@SubscribeEvent
	public void onConstructEntity(EntityEvent.EntityConstructing event)
	{
		Entity entity = event.entity;

		for (IEntityHookProvider provider : EntityHookModule.impl().getEntityHookProviders())
		{
			if (provider.canAttachToEntity(entity))
			{
				entity.registerExtendedProperties(provider.getId(), provider.getFactory().createHook());
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		Entity entity = event.entity;

		for (IEntityHookProvider<EntityHook> provider : EntityHookModule.impl().getEntityHookProviders())
		{
			if (provider.isAttachedToEntity(entity))
			{
				EntityHook hook = provider.getHook(entity);
				provider.loadHook(hook);

				if (!event.world.isRemote)
				{
					WorldServer worldServer = (WorldServer) entity.worldObj;

					PacketSyncHook packet = new PacketSyncHook(provider.getId(), provider.getHook(entity), entity);

					// Syncs the hook to every player tracking this entity
					for (EntityPlayer player : worldServer.getEntityTracker().getTrackingPlayers(entity))
					{
						UtilModule.NETWORK.sendTo(packet, (EntityPlayerMP) player);
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWorldLoaded(WorldEvent.Load event)
	{
		if (!event.world.isRemote)
		{
			event.world.addWorldAccess(EntityHookEventHandler.worldAccess);
		}
	}

	@SubscribeEvent
	public void onEntityUpdated(LivingEvent.LivingUpdateEvent event)
	{
		for (IEntityHookProvider<EntityHook> provider : EntityHookModule.impl().getEntityHookProviders())
		{
			if (provider.isAttachedToEntity(event.entity))
			{
				provider.updateHook(provider.getHook(event.entity));
			}
		}
	}
}
