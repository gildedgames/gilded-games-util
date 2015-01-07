package com.gildedgames.util.worldhook.common.test;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.util.worldhook.common.IWorldHook;

public class WorldTest implements IWorldHook
{
	
	protected World world;
	
	public boolean flag;
	
	public WorldTest(World world)
	{
		this.world = world;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setBoolean("test", this.flag);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.flag = input.getBoolean("test");
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
		if (this.flag == false)
		{
			this.flag = true;
			System.out.println("YESSSS");
		}
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

}
