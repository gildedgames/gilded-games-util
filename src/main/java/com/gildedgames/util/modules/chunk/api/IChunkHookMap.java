package com.gildedgames.util.modules.chunk.api;

import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import net.minecraft.util.math.ChunkPos;

public interface IChunkHookMap
{
	/**
	 * Called when a new hook belonging to this pool is loaded or created.
	 * @param hook The hook to add
	 * @param pos The hook's position in the world
	 */
	void addChunkHook(IChunkHook hook, ChunkPos pos);

	/**
	 * Called when a chunk at the specified coordinate is unloaded.
	 * @param pos The chunk's coordinates
	 */
	void removeChunkHook(ChunkPos pos);

	IChunkHook getHook(ChunkPos pos);

	boolean isEmpty();
}
