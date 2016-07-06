package com.gildedgames.util.modules.chunk.impl.pools;

import com.gildedgames.util.modules.chunk.api.IChunkHookMap;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookProvider;
import net.minecraft.util.math.ChunkPos;

import java.util.Collection;
import java.util.HashMap;

public class WorldHookPool
{
	private final HashMap<IChunkHookProvider, IChunkHookMap> pool = new HashMap<>();

	public WorldHookPool(Collection<IChunkHookProvider> providers)
	{
		for (IChunkHookProvider provider : providers)
		{
			this.pool.put(provider, new ChunkHookMap());
		}
	}

	public IChunkHook getHook(IChunkHookProvider provider, ChunkPos pos)
	{
		IChunkHookMap map = this.pool.get(provider);

		if (map == null)
		{
			return null;
		}

		return map.getHook(pos);
	}

	public void addHook(IChunkHookProvider provider, IChunkHook hook, ChunkPos pos)
	{
		IChunkHookMap map = this.pool.get(provider);

		if (map == null)
		{
			throw new IllegalArgumentException("Provider " + provider.getID().toString() + " isn't registered");
		}

		map.addChunkHook(hook, pos);
	}

	public void clear()
	{
		this.pool.clear();
	}

	public Collection<IChunkHookMap> getAllPools()
	{
		return this.pool.values();
	}
}
