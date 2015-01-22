package com.gildedgames.util.testutil;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.world.common.IWorldHook;
import com.gildedgames.util.world.common.world.IWorld;

public class TestWorldHook implements IWorldHook
{

	IWorld w;

	int id;

	private TestWorldHook()
	{

	}

	public TestWorldHook(IWorld w, int id)
	{
		this.w = w;
		this.id = id;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setInteger("id", this.id);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.id = input.getInteger("id");
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
	public IWorld getWorld()
	{
		return this.w;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}

		if (obj instanceof TestWorldHook)
		{
			return this.w.equals(((TestWorldHook) obj).getWorld());
		}
		return false;

	}

}
