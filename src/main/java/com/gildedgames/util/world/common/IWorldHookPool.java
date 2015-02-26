package com.gildedgames.util.world.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.world.common.world.IWorld;
import net.minecraft.world.World;

import java.util.Collection;

public interface IWorldHookPool<W extends IWorldHook> extends NBT
{
	
	W get(IWorld world);

	W get(World world);

	void clear();

	Collection<W> getWorlds();

	String getPoolName();

}
