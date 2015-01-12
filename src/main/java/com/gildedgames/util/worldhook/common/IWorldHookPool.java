package com.gildedgames.util.worldhook.common;

import java.util.Collection;

import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.NBT;
import com.gildedgames.util.worldhook.common.world.IWorld;

public interface IWorldHookPool<W extends IWorldHook> extends NBT
{
	
	W get(IWorld world);

	W get(World world);

	void clear();

	Collection<W> getWorlds();

	String getPoolName();

}
