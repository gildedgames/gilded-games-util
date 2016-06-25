package com.gildedgames.util.modules.chunk.impl;

import com.gildedgames.util.modules.chunk.api.ChunkServices;
import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookFactory;
import com.gildedgames.util.modules.chunk.impl.pools.WorldHookPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
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
	public void onWorldLoaded(WorldEvent.Load event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

		if (worldPool == null)
		{
			// Create our chunk pool for the dimension if we haven't already
			this.createWorldPool(event.getWorld());
		}
	}

	@SubscribeEvent
	public void onWorldUnloaded(WorldEvent.Unload event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

		if (worldPool != null)
		{
			worldPool.clear();

			this.pools.remove(event.getWorld().provider.getDimension());
		}
	}

	@SubscribeEvent
	public void onChunkLoaded(ChunkDataEvent.Load event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

		if (worldPool == null)
		{
			return;
		}

		long coord = this.getChunkCoord(event.getChunk());

		// Build each hook using our registered factories for this chunk
		for (IChunkHookFactory<IChunkHook> factory : this.factories)
		{
			IChunkHook hook = factory.createHook(event.getWorld(), event.getData());

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
		if (event.getWorld().isRemote)
		{
			return;
		}

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

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
		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

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

	public WorldHookPool getWorldPool(World world)
	{
		return this.pools.get(world.provider.getDimension());
	}

	/**
	 * Creates an empty pool for a dimension.
	 * @param world The dimension/world.
	 * @return A new empty pool.
	 */
	private WorldHookPool createWorldPool(World world)
	{
		WorldHookPool pool = new WorldHookPool();

		this.pools.put(world.provider.getDimension(), pool);

		return pool;
	}

	@Override
	public <T extends IChunkHook> T getHook(World world, BlockPos pos, Class<T> clazz)
	{
		return this.getHook(world, pos.getX() / 16, pos.getZ() / 16, clazz);
	}

	@Override
	public <T extends IChunkHook> T getHook(World world, ChunkPos pos, Class<T> clazz)
	{
		return this.getHook(world, pos.chunkXPos, pos.chunkZPos, clazz);
	}

	private <T extends IChunkHook> T getHook(World world, int x, int z, Class<T> clazz)
	{
		WorldHookPool pool = this.getWorldPool(world);

		if (pool != null)
		{
			IChunkHookPool hookPool = pool.getPool(clazz);

			if (hookPool != null)
			{
				return (T) hookPool.getHook(ChunkPos.chunkXZ2Int(x, z));
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
		return ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition);
	}

	/**
	 * Destroys all currently loaded pools.
	 */
	public void clean()
	{
		this.pools.clear();
	}
}
