package com.gildedgames.util.modules.chunk.api.hook;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;

public interface IChunkHookProvider<T extends IChunkHook>
{
	/**
	 * Creates a hook which links into a regular chunk.
	 * @return A new hook {@link T}
	 */
	T createHook(ChunkPos pos);

	ResourceLocation getID();
}
