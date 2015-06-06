package com.gildedgames.util.core.nbt;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IOManagerNotFoundException;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.overhead.IOManager;

public class NBTBridge implements IOBridge
{

	private final NBTTagCompound tag;

	public NBTBridge( NBTTagCompound tag)
	{
		this.tag = tag;
	}

	@Override
	public void setBoolean(String key, boolean value)
	{
		this.tag.setBoolean(key, value);
	}

	@Override
	public void setByte(String key, byte value)
	{
		this.tag.setByte(key, value);
	}

	@Override
	public void setShort(String key, short value)
	{
		this.tag.setShort(key, value);
	}

	@Override
	public void setInteger(String key, int value)
	{
		this.tag.setInteger(key, value);
	}

	@Override
	public void setLong(String key, long value)
	{
		this.tag.setLong(key, value);
	}

	@Override
	public void setFloat(String key, float value)
	{
		this.tag.setFloat(key, value);
	}

	@Override
	public void setDouble(String key, double value)
	{
		this.tag.setDouble(key, value);
	}

	@Override
	public void setString(String key, String value)
	{
		this.tag.setString(key, value);
	}

	@Override
	public void setByteArray(String key, byte[] value)
	{
		this.tag.setByteArray(key, value);
	}

	@Override
	public byte getByte(String key)
	{
		return this.tag.getByte(key);
	}

	@Override
	public short getShort(String key)
	{
		return this.tag.getShort(key);
	}

	@Override
	public int getInteger(String key)
	{
		return this.tag.getInteger(key);
	}

	@Override
	public long getLong(String key)
	{
		return this.tag.getLong(key);
	}

	@Override
	public float getFloat(String key)
	{
		return this.tag.getFloat(key);
	}

	@Override
	public double getDouble(String key)
	{
		return this.tag.getDouble(key);
	}

	@Override
	public String getString(String key)
	{
		return this.tag.getString(key);
	}

	@Override
	public byte[] getByteArray(String key)
	{
		return this.tag.getByteArray(key);
	}

	@Override
	public boolean getBoolean(String key)
	{
		return this.tag.getBoolean(key);
	}

	@Override
	public byte[] getBytes()
	{
		return NBTHelper.fromTag(this.tag);
	}

	@Override
	public Class<?> getSerializedClass(String key)
	{
		String registryID = this.getString("IOManagerID" + key);
		int classID = this.getInteger(key);

		IOManager manager = IOCore.io().getManager(registryID);

		if (manager == null)
		{
			throw new IOManagerNotFoundException("Manager was not found:" + registryID);
		}

		return manager.getRegistry().getClass(registryID, classID);
	}

	@Override
	public void setSerializedClass(String key, Class<?> classToWrite)
	{
		IOManager manager = IOCore.io().getManager(classToWrite);
		
		if (manager == null)
		{
			System.out.println("HELLOOOOOOOOOOOOOOO!!!!! " + classToWrite);
		}

		int classID = manager.getRegistry().getID(classToWrite);

		this.setString("IOManagerID" + key, manager.getID());
		this.setInteger(key, classID);
	}

}
