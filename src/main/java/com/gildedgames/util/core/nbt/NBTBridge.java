package com.gildedgames.util.core.nbt;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IOManagerNotFoundException;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.IOObserver;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.overhead.IOManager;

import net.minecraft.nbt.NBTTagCompound;

public class NBTBridge implements IOBridge
{

	private final NBTTagCompound tag;

	public final static NBTBridgeFactory factory = new NBTBridgeFactory();

	public NBTBridge(NBTTagCompound tag)
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
	public void setIO(String key, IO<IOBridge, IOBridge> io)
	{
		final NBTTagCompound in = new NBTTagCompound();
		final boolean isWritten = io != null;
		in.setBoolean("isWritten", isWritten);
		if (isWritten)
		{
			final NBTBridge writer = new NBTBridge(in);
			IOCore.io().set("clazzq", writer, factory, io);
		}
		this.tag.setTag(key, in);
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
	public <T extends IO<IOBridge, IOBridge>> T getIO(String key)
	{
		NBTTagCompound from = this.tag.getCompoundTag(key);
		if (from.getBoolean("isWritten"))
		{
			final NBTBridge reader = new NBTBridge(from);
			return IOCore.io().get("clazzq", reader, factory);
		}
		return null;
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

		int classID = manager.getRegistry().getID(classToWrite);

		this.setString("IOManagerID" + key, manager.getID());
		this.setInteger(key, classID);
	}

	public static class NBTBridgeFactory implements IOFactory<IOBridge, IOBridge>
	{

		@Override
		public IOBridge createInput(byte[] reading)
		{

			try
			{
				DataInputStream stream = new DataInputStream(new ByteArrayInputStream(reading));
				NBTTagCompound tag = NBTHelper.readInputNBT(stream);
				stream.close();
				return new NBTBridge(tag);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			return this.createOutput();
		}

		@Override
		public IOBridge createOutput()
		{
			return new NBTBridge(new NBTTagCompound());
		}

		@Override
		public IOBridge createInputBridge(IOBridge input)
		{
			return input;
		}

		@Override
		public IOBridge createOutputBridge(IOBridge output)
		{
			return output;
		}

		@Override
		public List<IOObserver<IOBridge, IOBridge>> getObservers()
		{
			return Collections.emptyList();
		}

	}

}
