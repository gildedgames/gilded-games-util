package com.gildedgames.util.core.nbt;

import com.gildedgames.util.io_manager.io.IOData;
import com.gildedgames.util.io_manager.io.IOFile;
import com.google.common.base.Optional;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;

public class NBTFile implements IOFile<NBTTagCompound, NBTTagCompound>
{

	protected final File directory;

	protected final NBT nbt;

	protected final Class<?> dataClass;

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
