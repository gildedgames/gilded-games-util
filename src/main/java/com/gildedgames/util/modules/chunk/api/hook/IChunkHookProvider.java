package com.gildedgames.util.modules.chunk.api.hook;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IChunkHookProvider<T extends IChunkHook>
{
	/**
	 * Creates a hook which links into a regular chunk.
	 * @param world The chunk's world
	 * @param tag The chunk's NBT
	 * @return A new hook {@link T}
	 */
	T createHook(World world, NBTTagCompound tag);

	ResourceLocation getID();
}
