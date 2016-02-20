package com.gildedgames.util.modules.chunk.api;

import com.gildedgames.util.modules.chunk.common.hook.IChunkHook;
import com.gildedgames.util.modules.chunk.common.hook.IChunkHookFactory;
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

	IChunkHook getHook(World world, BlockPos pos, Class<? extends IChunkHook> clazz);

	IChunkHook getHook(World world, ChunkCoordIntPair pos, Class<? extends IChunkHook> clazz);
}
