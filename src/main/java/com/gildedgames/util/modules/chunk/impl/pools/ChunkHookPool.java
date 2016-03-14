package com.gildedgames.util.modules.chunk.impl.pools;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.LongHashMap;

import java.util.HashMap;

public class ChunkHookPool implements IChunkHookPool
{
	private final HashMap<Long, IChunkHook> loadedHooks = new HashMap<>();

	@Override
	public void addChunkHook(IChunkHook hook, long coord)
	{
		if (this.loadedHooks.containsKey(coord))
		{
			UtilModule.logger().warn("Chunk hook at " + coord + " was already loaded, overwriting (this shouldn't happen)");
		}

		this.loadedHooks.put(coord, hook);
	}

	@Override
	public void saveChunkHook(NBTTagCompound tag, long coord)
	{
		if (this.loadedHooks.containsKey(coord))
		{
			IChunkHook hook = this.getHook(coord);
			hook.write(tag);
		}
	}

	@Override
	public void removeChunkHook(long coord)
	{
		if (this.loadedHooks.containsKey(coord))
		{
			this.loadedHooks.remove(coord);
		}
	}

	@Override
	public IChunkHook getHook(long coord)
	{
		return this.loadedHooks.get(coord);
	}
}
