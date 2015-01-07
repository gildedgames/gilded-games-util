package com.gildedgames.util.worldhook.common;

import net.minecraft.world.World;

import com.gildedgames.util.io_manager.io.NBT;

public interface IWorldHook extends NBT
{

	void onLoad();

	void onUnload();

	void onSave();

	void onUpdate();

	World getWorld();

}
