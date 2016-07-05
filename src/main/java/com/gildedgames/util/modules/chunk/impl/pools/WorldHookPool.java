package com.gildedgames.util.modules.chunk.impl.pools;

import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookProvider;

import java.util.Collection;
import java.util.HashMap;

public class WorldHookPool
{
	private final HashMap<IChunkHookProvider, IChunkHookPool> pool = new HashMap<>();

	public IChunkHookPool getPool(IChunkHookProvider provider)
	{
		return this.pool.get(provider);
	}

	public void addPool(IChunkHookProvider provider, IChunkHookPool pool)
	{
		this.pool.put(provider, pool);
	}

	public void clear()
	{
		this.pool.clear();
	}

	public Collection<IChunkHookPool> getAllPools()
	{
		return this.pool.values();
	}
}
