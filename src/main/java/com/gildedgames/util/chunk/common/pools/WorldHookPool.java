package com.gildedgames.util.chunk.common.pools;

import com.gildedgames.util.chunk.common.hook.IChunkHook;
import com.gildedgames.util.chunk.common.pools.IChunkHookPool;

import java.util.Collection;
import java.util.HashMap;

public class WorldHookPool
{
	private final HashMap<Class<? extends IChunkHook>, IChunkHookPool> pool = new HashMap<>();

	public <T extends IChunkHook> IChunkHookPool getPool(Class<T> clazz)
	{
		return this.pool.get(clazz);
	}

	public <T extends IChunkHook> IChunkHookPool addPool(Class<T> clazz, IChunkHookPool pool)
	{
		this.pool.put(clazz, pool);

		return pool;
	}

	public Collection<IChunkHookPool> getAllPools()
	{
		return this.pool.values();
	}
}
