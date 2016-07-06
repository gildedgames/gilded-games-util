package com.gildedgames.util.modules.chunk.api;

import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public interface ChunkServices
{
	/**
	 * Registers a chunk hook provider.
	 * @param provider The provider to register
	 * @throws IllegalArgumentException If another provider is already registered with the same ID
	 */
	void registerChunkHookProvider(IChunkHookProvider provider);

	<T extends IChunkHook> T getHook(World world, BlockPos pos, IChunkHookProvider<T> clazz);

	<T extends IChunkHook> T getHook(World world, ChunkPos pos, IChunkHookProvider<T> clazz);
}
