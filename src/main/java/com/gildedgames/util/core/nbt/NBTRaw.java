package com.gildedgames.util.core.nbt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import com.gildedgames.util.core.nbt.util.NBTUtil;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class NBTRaw extends NBTTagCompound
{
	
	private NBTTagCompound tag;
	
	private ByteBuf buf;
	
	private PacketBuffer packetBuffer;
	
	private NBTFactory factory;
	
	public NBTRaw(NBTFactory factory)
	{
		this.factory = factory;
		
		this.buf = Unpooled.buffer();
		
		this.packetBuffer = new PacketBuffer(this.buf);
	}

	public NBTRaw(NBTFactory factory, ByteBuf buf)
	{
		this.factory = factory;

		this.buf = buf;
		
		this.packetBuffer = new PacketBuffer(this.buf);
	}
	
	public byte[] getBytes()
	{
		return this.buf.hasArray() ? this.buf.array() : null;
	}
	
	@Override
	public void setTag(String key, NBTBase value)
    {
		NBTUtil.writeNBTBaseToBuffer(this.packetBuffer, value);
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
    public void setByteArray(String key, byte[] value)
    {
    	this.packetBuffer.writeByteArray(value);
    }

    @Override
    public void setIntArray(String key, int[] value)
    {
    	NBTTagIntArray intArray = new NBTTagIntArray(value);
    	
    	this.setTag(key, intArray);
    }
    
    @Override
    public void setBoolean(String key, boolean value)
    {
        this.setByte(key, (byte)(value ? 1 : 0));
    }
	
    @Override
    public NBTBase getTag(String key)
    {
    	NBTBase nbt = null;
    	
		try
		{
			nbt = NBTUtil.readNBTBaseFromBuffer(this.packetBuffer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    	
    	return nbt;
    }

    @Override
    public byte getTagType(String key)
    {
    	NBTBase nbt = this.getTag(key);
    	
        return nbt != null ? nbt.getId() : 0;
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
        return this.packetBuffer.readByteArray();
    }

    @Override
    public int[] getIntArray(String key)
    {
    	NBTTagIntArray intArray = (NBTTagIntArray)this.getTag(key);
    	
        return intArray.getIntArray();
    }

    @Override
    public NBTTagCompound getCompoundTag(String key)
    {
        return ByteBufUtils.readTag(this.buf);
    }

    @Override
    public NBTTagList getTagList(String key, int type)
    {
    	byte tagType = this.getTagType(key);
    	NBTTagList nbttaglist = (NBTTagList)this.getTag(key);
    	
    	if (tagType != 9)
        {
            return new NBTTagList();
        }
        else
        {
            return nbttaglist.tagCount() > 0 && nbttaglist.getTagType() != type ? new NBTTagList() : nbttaglist;
        }
    }

    @Override
    public boolean getBoolean(String key)
    {
        return this.buf.readBoolean();
    }
	
}
