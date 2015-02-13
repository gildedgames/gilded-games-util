package com.gildedgames.util.core.nbt;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOData;
import com.google.common.base.Optional;

public class NBTFile implements IOFile<NBTTagCompound, NBTTagCompound>
{

	protected File directory;

	protected NBT nbt;

	protected Class<?> dataClass;

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
	public Class<?> getDataClass()
	{
		return this.dataClass;
	}

	@Override
	public Optional<IOData<NBTTagCompound, NBTTagCompound>> getSubData()
	{
		return Optional.absent();
	}

	@Override
	public void setSubData(IOData<NBTTagCompound, NBTTagCompound> metadata)
	{

	}

}
