package com.gildedgames.util.io_manager.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.factory.IReaderWriterFactory;
import com.gildedgames.util.io_manager.io.IOFile;

public class NBTFactory implements IReaderWriterFactory<IOFile<NBTTagCompound, NBTTagCompound>, NBTTagCompound, NBTTagCompound>
{

	@Override
	public NBTTagCompound getReader(DataInputStream input, IOManager manager)
	{
		try
		{
			return NBTHelper.readInputNBT(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return new NBTTagCompound();
	}

	@Override
	public NBTTagCompound getWriter(DataOutputStream output, IOManager manager)
	{
		return new NBTTagCompound();
	}

	@Override
	public File getFileFromName(IOFile<NBTTagCompound, NBTTagCompound> data, String name)
	{
		return null;
	}

	@Override
	public void preReading(IOFile<NBTTagCompound, NBTTagCompound> data, File from, NBTTagCompound input)
	{
		
	}

	@Override
	public void finishWriting(DataOutputStream input, NBTTagCompound output)
	{
		try
		{
			NBTHelper.writeOutputNBT(output, input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
