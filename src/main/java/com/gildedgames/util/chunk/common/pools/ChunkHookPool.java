package com.gildedgames.util.chunk.common.pools;

import com.gildedgames.util.chunk.common.hook.IChunkHook;
import com.gildedgames.util.core.UtilCore;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class ChunkHookPool implements IChunkHookPool
{
	private final Map<Long, IChunkHook> loadedHooks = new HashMap<>();

	@Override
	public void addChunkHook(IChunkHook hook, long coord)
	{
		if (this.loadedHooks.containsKey(coord))
		{
			UtilCore.print("Chunk hook at " + coord + " was already loaded, overwriting (this shouldn't happen)");
		}

		this.loadedHooks.put(coord, hook);
	}

	@Override
	public void saveChunkHook(NBTTagCompound tag, long coord)
	{
		if (!this.loadedHooks.containsKey(coord))
		{
			// UtilCore.print("Chunk " + coord + " is missing a chunk hook, ignoring chunk save event... (this shouldn't happen)");

			return;
		}

		IChunkHook hook = this.getHook(coord);
		hook.write(tag);
	}

	@Override
	public void removeChunkHook(long coord)
	{
		if (!this.loadedHooks.containsKey(coord))
		{
			// UtilCore.print("Chunk " + coord + " is missing a chunk hook, ignoring chunk unload event... (this shouldn't happen)");

			return;
		}

		this.loadedHooks.remove(coord);
	}

	@Override
	public IChunkHook getHook(long coord)
	{
		return this.loadedHooks.get(coord);
	}
}
