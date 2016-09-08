package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.UtilModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InstanceCapabilityManager
{

	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(InstanceCapabilityManager.class);

		CapabilityManager.INSTANCE.register(IPlayerInstances.class, new PlayerInstances.Storage(), PlayerInstances.class);
	}

	@SubscribeEvent
	public static void onEntityLoad(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			event.addCapability(UtilModule.getResource("PlayerInstances"), new PlayerInstancesProvider((EntityPlayer) event.getEntity()));
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		IPlayerInstances oldPlayer = InstanceModule.INSTANCE.getPlayer(event.getOriginal());

		if (oldPlayer != null)
		{
			IPlayerInstances newPlayer = InstanceModule.INSTANCE.getPlayer((EntityPlayer) event.getEntity());

			Capability.IStorage<IPlayerInstances> storage = InstanceModule.PLAYER_INSTANCES.getStorage();

			NBTBase state = storage.writeNBT(InstanceModule.PLAYER_INSTANCES, oldPlayer, null);

			storage.readNBT(InstanceModule.PLAYER_INSTANCES, newPlayer, null, state);
		}
	}

}
