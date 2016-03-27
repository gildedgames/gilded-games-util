package com.gildedgames.util.modules.chunk.api.hook;

import com.gildedgames.util.core.nbt.NBT;

public interface IChunkHook extends NBT
{
	/**
	 * Returns the unique identifier for this {@link IChunkHook}, prefixed by the registering
	 * mod's id.
	 */
	String getName();
}
