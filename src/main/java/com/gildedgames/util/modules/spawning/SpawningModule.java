package com.gildedgames.util.modules.spawning;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.spawning.util.DefaultSpawnSettings;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.ArrayList;
import java.util.List;

public class SpawningModule extends Module
{
	public static final SpawningModule INSTANCE = new SpawningModule();

	private static List<SpawnManager> spawnManagers = new ArrayList<>();

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
		List<SpawnManager> selected = new ArrayList<>();
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
					spawnManager.tickSpawning(world, GroupModule.locate().getPlayers().getPlayerHooks());//It's kinda ewwy how it uses GroupCore here admittedly
				}
			}
		}
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{
		for (SpawnManager spawnManager : spawnManagers)
		{
			spawnManager.clear();
		}
	}
}
