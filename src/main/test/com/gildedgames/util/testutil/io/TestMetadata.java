package com.gildedgames.util.testutil.io;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.io.IOData;
import com.google.common.base.Optional;

public class TestMetadata implements IOData<NBTTagCompound, NBTTagCompound>, NBT
{

	public int id;

	private TestMetadata()
	{

	}

	public TestMetadata(int id)
	{
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
	public Optional<IOData<NBTTagCompound, NBTTagCompound>> getSubData()
	{
		return Optional.absent();
	}

	@Override
	public void setSubData(IOData<NBTTagCompound, NBTTagCompound> metadata)
	{
		Assert.fail("Trying to set metadata in an object that doesn't have any");
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof TestMetadata)
		{
			return this.id == ((TestMetadata) obj).id;
		}
		return false;
	}

}
