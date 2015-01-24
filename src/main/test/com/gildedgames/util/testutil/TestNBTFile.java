package com.gildedgames.util.testutil;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.google.common.base.Optional;

public class TestNBTFile implements IOFile<NBTTagCompound, NBTTagCompound>
{

	private int id;

	private IOFileMetadata<NBTTagCompound, NBTTagCompound> metadata;

	private TestNBTFile()
	{

	}

	public TestNBTFile(int id, int metaId)
	{
		this.id = id;

		this.metadata = new TestMetadata(metaId);
	}

	@Override
	public void read(NBTTagCompound reader)
	{
		this.id = reader.getInteger("id");
	}

	@Override
	public void write(NBTTagCompound writer)
	{
		writer.setInteger("id", this.id);
	}

	@Override
	public Class<?> getDataClass()
	{
		return this.getClass();
	}

	@Override
	public String getFileExtension()
	{
		return "test";
	}

	@Override
	public String getDirectoryName()
	{
		return "test";
	}

	@Override
	public Optional<IOFileMetadata<NBTTagCompound, NBTTagCompound>> getMetadata()
	{
		return Optional.of(this.metadata);
	}

	@Override
	public void setMetadata(IOFileMetadata<NBTTagCompound, NBTTagCompound> metadata)
	{
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof TestNBTFile)
		{
			TestNBTFile casted = (TestNBTFile) obj;
			return this.id == casted.id && this.metadata.equals(casted.metadata);
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "ioFile " + this.id;
	}

}
