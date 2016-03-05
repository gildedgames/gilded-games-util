package com.gildedgames.util.modules.chunk.api.hook;

import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IChunkHookFactory<T extends IChunkHook>
{
	/**
	 * Creates a hook which links into a regular chunk.
	 * @param world The chunk's world
	 * @param tag The chunk's NBT
	 * @return A new hook {@link T}
	 */
	T createHook(World world, NBTTagCompound tag);

	/**
	 * Creates a pool which stores this factory's hooks.
	 * @return An empty {@link IChunkHookPool <T>}
	 */
	IChunkHookPool createPool();
}
