package com.gildedgames.util.worldhook.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.IO;

public interface IWorldHook extends IO<NBTTagCompound, NBTTagCompound>
{
	
	void onLoad();
	
	void onUnload();
	
	void onSave();
	
	void onUpdate();

	World getWorld();
	
}
