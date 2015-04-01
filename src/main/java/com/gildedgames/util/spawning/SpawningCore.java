package com.gildedgames.util.spawning;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.spawning.util.DefaultSpawnSettings;

public class SpawningCore implements ICore
{
	
	private static List<SpawnManager> spawnManagers = new ArrayList<SpawnManager>();

	public static SpawnManager createAndRegisterSpawnManager(int dimensionId)
	{
		return createAndRegisterSpawnManager(dimensionId, new DefaultSpawnSettings());
	}

	public static SpawnManager createAndRegisterSpawnManager(int dimensionId, SpawnSettings settings)
	{
		SpawnManager s = new SpawnManager(dimensionId, settings);
		spawnManagers.add(s);
		return s;
	}

	public static List<SpawnManager> getSpawnManagersFor(int dimensionId)
	{
		List<SpawnManager> selected = new ArrayList<SpawnManager>();
		for (SpawnManager spawnManager : spawnManagers)
		{
			if (spawnManager.getDimensionId() == dimensionId)
			{
				selected.add(spawnManager);
			}
		}
		return selected;
	}

	/**
	 * Add a block that entities should not be able to spawn on.
	 */
	public static void registerBlacklistedBlock(Block block)
	{
		SpawnManager.registerBlacklistedBlock(block);
	}

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			World world = event.world;
			if (!world.isRemote)
			{
				List<SpawnManager> spawnManagers = getSpawnManagersFor(world.provider.getDimensionId());
				for (SpawnManager spawnManager : spawnManagers)
				{
					spawnManager.tickSpawning(world, GroupCore.locate().getPlayers().getPlayerHooks());//It's kinda ewwy how it uses GroupCore here admittedly
				}
			}
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{
		for (SpawnManager spawnManager : spawnManagers)
		{
			spawnManager.clear();
		}
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{

	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{

	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{

	}

}
