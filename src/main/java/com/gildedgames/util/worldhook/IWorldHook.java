package com.gildedgames.util.worldhook;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.IO;

public interface IWorldHook extends IO<NBTTagCompound, NBTTagCompound>
{
	
	public void onUpdate();

	public World getWorld();
	
}
