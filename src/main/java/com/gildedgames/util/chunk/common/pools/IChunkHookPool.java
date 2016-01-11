package com.gildedgames.util.chunk.common.pools;

import com.gildedgames.util.chunk.common.hook.IChunkHook;
import net.minecraft.nbt.NBTTagCompound;

public interface IChunkHookPool
{
	/**
	 * Called when a new hook belonging to this pool is loaded or created.
	 * @param hook The hook to add
	 * @param coord The hook's position in the world
	 */
	void addChunkHook(IChunkHook hook, long coord);

	/**
	 * Called when a chunk at the specified coordinate is saved.
	 * @param tag The chunk's NBT
	 * @param coord The chunk's coordinates
	 */
	void saveChunkHook(NBTTagCompound tag, long coord);

	/**
	 * Called when a chunk at the specified coordinate is unloaded.
	 * @param coord The chunk's coordinates
	 */
	void removeChunkHook(long coord);

	IChunkHook getHook(long coord);
}
