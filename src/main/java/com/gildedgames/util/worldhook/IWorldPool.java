package com.gildedgames.util.worldhook;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.IO;

public interface IWorldPool<W extends IWorldHook> extends IO<NBTTagCompound, NBTTagCompound>
{
	
	public W get(World world);

	public Collection<W> getWorlds();

	public void addHook(World world);

}
