package com.gildedgames.util.modules.chunk.impl;

import com.gildedgames.util.modules.chunk.api.ChunkServices;
import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookFactory;
import com.gildedgames.util.modules.chunk.impl.pools.WorldHookPool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkServicesImpl implements ChunkServices
{
	private final Map<Integer, WorldHookPool> pools = new HashMap<>();

	private final List<IChunkHookFactory<IChunkHook>> factories = new ArrayList<>();

	@SubscribeEvent
	public void onChunkLoaded(ChunkDataEvent.Load event)
	{
		WorldHookPool worldPool = this.getWorldPool(event.world);

		if (worldPool == null)
		{
			// Create our chunk pool for the dimension if we haven't already
			worldPool = this.createWorldPool(event.world);
		}

		long coord = this.getChunkCoord(event.getChunk());

		// Build each hook using our registered factories for this chunk
		for (IChunkHookFactory<IChunkHook> factory : this.factories)
		{
			IChunkHook hook = factory.createHook(event.world, event.getData());

			IChunkHookPool hookPool = worldPool.getPool(hook.getClass());

			if (hookPool == null)
			{
				hookPool = worldPool.addPool(hook.getClass(), factory.createPool());
			}

			hookPool.addChunkHook(hook, coord);
		}
	}

	@SubscribeEvent
	public void onChunkUnloaded(ChunkEvent.Unload event)
	{
		WorldHookPool worldPool = this.getWorldPool(event.world);

		if (worldPool != null)
		{
			for (IChunkHookPool hookPool : worldPool.getAllPools())
			{
				hookPool.removeChunkHook(this.getChunkCoord(event.getChunk()));
			}
		}
	}

	@SubscribeEvent
	public void onChunkSaved(ChunkDataEvent.Save event)
	{
		WorldHookPool worldPool = this.getWorldPool(event.world);

		if (worldPool != null)
		{
			for (IChunkHookPool hookPool : worldPool.getAllPools())
			{
				hookPool.saveChunkHook(event.getData(), this.getChunkCoord(event.getChunk()));
			}
		}
	}

	@Override
	public void registerHookFactory(IChunkHookFactory<IChunkHook> factory)
	{
		if (this.factories.contains(factory))
		{
			throw new IllegalArgumentException("Factory " + factory.getClass().getCanonicalName() + " is already registered!");
		}

		this.factories.add(factory);
	}

	private WorldHookPool getWorldPool(World world)
	{
		return this.pools.get(world.provider.getDimensionId());
	}

	/**
	 * Creates an empty pool for a dimension.
	 * @param world The dimension/world.
	 * @return A new empty pool.
	 */
	private WorldHookPool createWorldPool(World world)
	{
		WorldHookPool pool = new WorldHookPool();

		this.pools.put(world.provider.getDimensionId(), pool);

		return pool;
	}

	@Override
	public IChunkHook getHook(World world, BlockPos pos, Class<? extends IChunkHook> clazz)
	{
		return this.getHook(world, pos.getX() / 16, pos.getZ() / 16, clazz);
	}

	@Override
	public IChunkHook getHook(World world, ChunkCoordIntPair pos, Class<? extends IChunkHook> clazz)
	{
		return this.getHook(world, pos.chunkXPos, pos.chunkZPos, clazz);
	}

	private IChunkHook getHook(World world, int x, int z, Class<? extends IChunkHook> clazz)
	{
		WorldHookPool pool = this.getWorldPool(world);

		if (pool != null)
		{
			IChunkHookPool hookPool = pool.getPool(clazz);

			if (hookPool != null)
			{
				return hookPool.getHook(ChunkCoordIntPair.chunkXZ2Int(x, z));
			}
		}

		return null;
	}

	/**
	 * @param chunk The chunk
	 * @return Returns a unique identifier that represents the chunk's position.
	 */
	private long getChunkCoord(Chunk chunk)
	{
		return ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition);
	}

	/**
	 * Destroys all currently loaded pools.
	 */
	public void clean()
	{
		this.pools.clear();
	}
}
