package com.gildedgames.util.worldhook.common;

import com.gildedgames.util.io_manager.io.NBT;
import com.gildedgames.util.worldhook.common.world.IWorld;

public interface IWorldHook extends NBT
{
	
	void onLoad();
	
	void onUnload();
	
	void onSave();
	
	void onUpdate();

	IWorld getWorld();
	
}
