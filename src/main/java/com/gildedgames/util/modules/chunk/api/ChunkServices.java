package com.gildedgames.util.modules.chunk.api;

import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookFactory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public interface ChunkServices
{
	/**
	 * Registers a chunk hook factory. The factory is responsible for the creation of it's own chunk hooks from NBT.
	 * @param factory The factory to register.
	 */
	void registerHookFactory(IChunkHookFactory<IChunkHook> factory);

	<T extends IChunkHook> T getHook(World world, BlockPos pos, Class<T> clazz);

	<T extends IChunkHook> T getHook(World world, ChunkCoordIntPair pos, Class<T> clazz);
}
