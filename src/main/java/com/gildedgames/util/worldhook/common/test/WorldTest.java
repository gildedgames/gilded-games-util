package com.gildedgames.util.worldhook.common.test;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.worldhook.common.IWorldHook;
import com.gildedgames.util.worldhook.common.world.IWorld;

public class WorldTest implements IWorldHook
{
	
	protected IWorld world;
	
	public boolean flag;
	
	public WorldTest(IWorld world)
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
		}
	}

	@Override
	public IWorld getWorld()
	{
		return this.world;
	}

}
