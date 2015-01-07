package com.gildedgames.util.worldhook.common;

import java.util.Collection;

import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.NBT;

public interface IWorldPool<W extends IWorldHook> extends NBT
{
	
	W get(World world);
	
	void clear();

	Collection<W> getWorlds();

}
