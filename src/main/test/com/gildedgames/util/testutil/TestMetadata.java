package com.gildedgames.util.testutil;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;

import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.google.common.base.Optional;

public class TestMetadata implements IOFileMetadata<NBTTagCompound, NBTTagCompound>
{

	int id;

	File file;

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
	public void setFileLocation(File file)
	{
		this.file = file;
	}

	@Override
	public Optional<IOFileMetadata<NBTTagCompound, NBTTagCompound>> getMetadata()
	{
		return Optional.absent();
	}

	@Override
	public void setMetadata(IOFileMetadata<NBTTagCompound, NBTTagCompound> metadata)
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
