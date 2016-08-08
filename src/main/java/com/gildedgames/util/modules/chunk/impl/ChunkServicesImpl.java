package com.gildedgames.util.modules.chunk.impl;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.chunk.api.ChunkServices;
import com.gildedgames.util.modules.chunk.api.IChunkHookMap;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookProvider;
import com.gildedgames.util.modules.chunk.impl.pools.WorldHookPool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ChunkServicesImpl implements ChunkServices
{
	private static final ResourceLocation DATA_KEY = new ResourceLocation(UtilModule.MOD_ID, "hooks");

	private final Map<Integer, WorldHookPool> pools = new HashMap<>();

	private final HashMap<ResourceLocation, IChunkHookProvider> providers = new HashMap<>();

	@SubscribeEvent
	public void onWorldLoaded(WorldEvent.Load event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}

		WorldHookPool pool = new WorldHookPool(this.providers.values());

		this.pools.put(event.getWorld().provider.getDimension(), pool);
	}

	@SubscribeEvent
	public void onWorldUnloaded(WorldEvent.Unload event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());
		worldPool.clear();

		this.pools.remove(event.getWorld().provider.getDimension());
	}

	@SubscribeEvent
	public void onChunkLoaded(ChunkEvent.Load event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}

		ChunkPos pos = event.getChunk().getChunkCoordIntPair();

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

		// Fixes an issue where WorldEvent.Load may not be called before chunk loading
		if (worldPool == null)
		{
			this.pools.put(event.getWorld().provider.getDimension(), worldPool = new WorldHookPool(this.providers.values()));
		}

		for (IChunkHookProvider provider : this.providers.values())
		{
			this.createOrGetHook(worldPool, provider, pos);
		}
	}

	@SubscribeEvent
	public void onChunkRead(ChunkDataEvent.Load event)
	{
		NBTTagCompound root = null;

		if (event.getData().hasKey(DATA_KEY.toString()))
		{
			root = event.getData().getCompoundTag(DATA_KEY.toString());
		}

		ChunkPos pos = event.getChunk().getChunkCoordIntPair();

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

		for (IChunkHookProvider provider : this.providers.values())
		{
			NBTTagCompound data = root != null && root.hasKey(provider.getID().toString()) ? root.getCompoundTag(provider.getID().toString()) : new NBTTagCompound();

			IChunkHook hook = this.createOrGetHook(worldPool, provider, pos);
			hook.read(data);
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
			for (IChunkHookMap hookPool : worldPool.getAllPools())
			{
				hookPool.removeChunkHook(event.getChunk().getChunkCoordIntPair());
			}
		}
	}

	@SubscribeEvent
	public void onChunkSaved(ChunkDataEvent.Save event)
	{
		NBTTagCompound root = new NBTTagCompound();

		ChunkPos pos = event.getChunk().getChunkCoordIntPair();

		WorldHookPool worldPool = this.getWorldPool(event.getWorld());

		for (IChunkHookProvider provider : this.providers.values())
		{
			IChunkHook hook = worldPool.getHook(provider, pos);

			if (hook == null)
			{
				continue;
			}

			NBTTagCompound data = new NBTTagCompound();
			hook.write(data);

			root.setTag(provider.getID().toString(), data);
		}

		event.getData().setTag(DATA_KEY.toString(), root);
	}

	@Override
	public void registerChunkHookProvider(IChunkHookProvider provider)
	{
		if (this.providers.containsKey(provider.getID()))
		{
			throw new IllegalArgumentException("Provider " + provider.getID() + " is already registered");
		}

		this.providers.put(provider.getID(), provider);
	}

	public IChunkHook createOrGetHook(WorldHookPool worldPool, IChunkHookProvider provider, ChunkPos pos)
	{
		IChunkHook hook = worldPool.getHook(provider, pos);

		if (hook == null)
		{
			worldPool.addHook(provider, hook = provider.createHook(pos), pos);
		}

		return hook;
	}

	public WorldHookPool getWorldPool(World world)
	{
		return this.pools.get(world.provider.getDimension());
	}

	@Override
	public <T extends IChunkHook> T getHook(World world, BlockPos pos, IChunkHookProvider<T> provider)
	{
		return this.getHook(world, new ChunkPos(pos), provider);
	}

	@Override
	public <T extends IChunkHook> T getHook(World world, ChunkPos pos, IChunkHookProvider<T> provider)
	{
		WorldHookPool pool = this.getWorldPool(world);

		return (T) pool.getHook(provider, pos);
	}

	/**
	 * Destroys all currently loaded pools.
	 */
	public void clean()
	{
		this.pools.clear();
	}
}
