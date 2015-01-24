package com.gildedgames.util.io_manager.util.nbt;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.io.NBT;
import com.google.common.base.Optional;

public class NBTFile implements IOFile<NBTTagCompound, NBTTagCompound>
{

	protected File directory;

	protected NBT nbt;

	protected Class dataClass;

	public NBTFile(File directory, NBT nbt, Class dataClass)
	{
		this.directory = directory;
		this.nbt = nbt;
		this.dataClass = dataClass;
	}

	@Override
	public void read(NBTTagCompound reader)
	{
		this.nbt.read(reader);
	}

	@Override
	public void write(NBTTagCompound writer)
	{
		this.nbt.write(writer);
	}

	@Override
	public String getFileExtension()
	{
		return "dat";
	}

	@Override
	public String getDirectoryName()
	{
		return this.directory.getName();
	}

	@Override
	public Class getDataClass()
	{
		return this.dataClass;
	}

	@Override
	public Optional<IOFileMetadata<NBTTagCompound, NBTTagCompound>> getMetadata()
	{
		return Optional.absent();
	}

	@Override
	public void setMetadata(IOFileMetadata<NBTTagCompound, NBTTagCompound> metadata)
	{

	}

}
