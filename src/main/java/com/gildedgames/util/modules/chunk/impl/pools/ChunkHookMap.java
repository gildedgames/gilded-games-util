package com.gildedgames.util.modules.chunk.impl.pools;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.chunk.api.IChunkHookMap;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import net.minecraft.util.math.ChunkPos;

import java.util.HashMap;

public class ChunkHookMap implements IChunkHookMap
{
	private final HashMap<Long, IChunkHook> loadedHooks = new HashMap<>();

	@Override
	public void addChunkHook(IChunkHook hook, ChunkPos pos)
	{
		long id = ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos);

		if (this.loadedHooks.containsKey(id))
		{
			UtilModule.logger().warn("Chunk hook at " + pos.toString() + " was already loaded, overwriting (this shouldn't happen)");
		}

		this.loadedHooks.put(id, hook);
	}

	@Override
	public void removeChunkHook(ChunkPos pos)
	{
		long id = ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos);

		if (this.loadedHooks.containsKey(id))
		{
			this.loadedHooks.remove(id);
		}
	}

	@Override
	public IChunkHook getHook(ChunkPos pos)
	{
		long id = ChunkPos.asLong(pos.chunkXPos, pos.chunkZPos);

		return this.loadedHooks.get(id);
	}

	@Override
	public boolean isEmpty()
	{
		return this.loadedHooks.size() <= 0;
	}
}
