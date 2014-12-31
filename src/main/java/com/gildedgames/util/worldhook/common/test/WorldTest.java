package com.gildedgames.util.worldhook.common.test;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.util.worldhook.common.IWorldHook;

public class WorldTest implements IWorldHook
{
	
	protected World world;
	
	public WorldTest(World world)
	{
		this.world = world;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		
	}

	@Override
	public void read(NBTTagCompound input)
	{
		
	}

	@Override
	public void onLoad()
	{
		
	}

	@Override
	public void onUnload()
	{
		
	}

	@Override
	public void onSave()
	{
		
	}

	@Override
	public void onUpdate()
	{
		
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

}
