package com.gildedgames.util.core.nbt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;

public class NBTFactory implements IOFactory<IOFile<NBTTagCompound, NBTTagCompound>, NBTTagCompound, NBTTagCompound>
{

	private int writeIndex, readIndex;

	@Override
	public NBTTagCompound getInput(byte[] input)
	{
		try
		{
			DataInputStream stream = new DataInputStream(new ByteArrayInputStream(input));
			NBTTagCompound tag = NBTHelper.readInputNBT(stream);
			stream.close();
			return tag;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return new NBTTagCompound();
	}

	@Override
	public NBTTagCompound getOutput()
	{
		return new NBTTagCompound();
	}

	@Override
	public Class<?> getSerializedClass(String key, NBTTagCompound input)
	{
		System.out.println(input);
		System.out.println(key);

		String registryID = input.getString("IOManagerID" + key);
		int classID = input.getInteger(key);

		IOManager manager = IOCore.io().getManager(registryID);

		return manager.getRegistry().getClass(registryID, classID);
	}

	@Override
	public void setSerializedClass(String key, NBTTagCompound output, Class<?> classToWrite)
	{
		IOManager manager = IOCore.io().getManager(classToWrite);

		int classID = manager.getRegistry().getID(classToWrite);

		output.setString("IOManagerID" + key, manager.getID());
		output.setInteger(key, classID);
	}

	@Override
	public Class<?> readSerializedClass(NBTTagCompound input)
	{
		String registryID = input.getString("IOManagerID" + this.readIndex);
		int classID = input.getInteger("IOFactoryClass" + this.readIndex);
		this.readIndex++;

		IOManager manager = IOCore.io().getManager(registryID);

		return manager.getRegistry().getClass(registryID, classID);
	}

	@Override
	public void writeSerializedClass(NBTTagCompound output, Class<?> classToWrite)
	{
		IOManager manager = IOCore.io().getManager(classToWrite);

		int classID = manager.getRegistry().getID(classToWrite);

		output.setString("IOManagerID" + this.writeIndex, manager.getID());
		output.setInteger("IOFactoryClass" + this.writeIndex, classID);
		this.writeIndex++;
	}

	@Override
	public File getFileFromName(IOFile<NBTTagCompound, NBTTagCompound> data, String name)
	{
		return new File(name);
	}

	@Override
	public void preReading(IOFile<NBTTagCompound, NBTTagCompound> data, File from, NBTTagCompound input)
	{

	}

	@Override
	public void preReadingMetadata(IOFileMetadata<NBTTagCompound, NBTTagCompound> metadata, File from, NBTTagCompound reader)
	{

	}

	@Override
	public byte[] getBytesFrom(NBTTagCompound writer)
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(byteStream);
		try
		{
			NBTHelper.writeOutputNBT(writer, stream);
			byte[] bytez = byteStream.toByteArray();
			stream.close();
			return bytez;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
