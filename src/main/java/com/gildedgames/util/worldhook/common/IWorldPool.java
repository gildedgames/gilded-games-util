package com.gildedgames.util.worldhook.common;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.IO;

public interface IWorldPool<W extends IWorldHook> extends IO<NBTTagCompound, NBTTagCompound>
{
	
	W get(World world);
	
	void clear();

	Collection<W> getWorlds();

}
