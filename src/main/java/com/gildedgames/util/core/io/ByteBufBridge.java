package com.gildedgames.util.core.io;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.overhead.IOManager;

public class ByteBufBridge implements IOBridge
{
	
	protected ByteBuf buf;
	
	public ByteBufBridge(ByteBuf buf)
	{
		this.buf = buf;
	}

	@Override
	public byte[] getBytes()
	{
		return this.buf.array();
	}

	@Override
	public Class<?> getSerializedClass(String key)
	{
		final String registryID = this.getString("");
		final int classID = this.getInteger("");

		final IOManager manager = IOCore.io().getManager(registryID);

		return manager.getRegistry().getClass(registryID, classID);
	}

	@Override
	public void setSerializedClass(String key, Class<?> classToWrite)
	{
		final IOManager manager = IOCore.io().getManager(classToWrite);

		final int classID = manager.getRegistry().getID(classToWrite);

		this.setString("", manager.getID());
		this.setInteger("", classID);
	}

	@Override
	public void setBoolean(String key, boolean value)
	{
		this.buf.writeBoolean(value);
	}

	@Override
	public void setByte(String key, byte value)
	{
		this.buf.writeByte(value);
	}

	@Override
	public void setShort(String key, short value)
	{
		this.buf.writeShort(value);
	}

	@Override
	public void setInteger(String key, int value)
	{
		this.buf.writeInt(value);
	}

	@Override
	public void setLong(String key, long value)
	{
		this.buf.writeLong(value);
	}

	@Override
	public void setFloat(String key, float value)
	{
		this.buf.writeFloat(value);
	}

	@Override
	public void setDouble(String key, double value)
	{
		this.buf.writeDouble(value);
	}

	@Override
	public void setString(String key, String value)
	{
		ByteBufUtils.writeUTF8String(this.buf, value);
	}

	@Override
	public void setByteArray(String key, byte[] array)
	{
		if (array == null)
		{
			this.buf.writeBoolean(false);
			
			return;
		}
		
		this.buf.writeBoolean(true);
		this.buf.writeInt(array.length);
		this.buf.writeBytes(array);
	}

	@Override
	public byte getByte(String key)
	{
		return this.buf.readByte();
	}

	@Override
	public short getShort(String key)
	{
		return this.buf.readShort();
	}

	@Override
	public int getInteger(String key)
	{
		return this.buf.readInt();
	}

	@Override
	public long getLong(String key)
	{
		return this.buf.readLong();
	}

	@Override
	public float getFloat(String key)
	{
		return this.buf.readFloat();
	}

	@Override
	public double getDouble(String key)
	{
		return this.buf.readDouble();
	}

	@Override
	public String getString(String key)
	{
		return ByteBufUtils.readUTF8String(this.buf);
	}

	@Override
	public byte[] getByteArray(String key)
	{
		if (!this.buf.readBoolean())
		{
			return null;
		}
		
		final int amount = this.buf.readInt();
		final byte[] bytes = new byte[amount];
		this.buf.readBytes(bytes);
		
		return bytes;
	}

	@Override
	public boolean getBoolean(String key)
	{
		return this.buf.readBoolean();
	}

}
