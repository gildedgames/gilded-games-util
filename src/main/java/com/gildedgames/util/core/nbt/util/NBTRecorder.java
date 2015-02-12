package com.gildedgames.util.core.nbt.util;

import java.util.ArrayList;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.gildedgames.util.io_manager.factory.InputRecorder;

public class NBTRecorder extends NBTTagCompound implements InputRecorder<NBTTagCompound>
{
	
	private NBTTagCompound tag;
	
	private ArrayList<String> recordedKeys = new ArrayList<String>();
	
	public NBTRecorder(NBTTagCompound input)
	{
		this.tag = input;
	}

	@Override
	public NBTTagCompound getInputWrapper()
	{
		return this;
	}

	@Override
	public ArrayList<String> getRecordedKeys()
	{
		return new ArrayList<String>(this.recordedKeys);
	}
	
	private void recordKey(String key)
	{
		this.recordedKeys.add(key);
	}

    @Override
    public NBTBase getTag(String key)
    {
    	this.recordKey(key);
    	
    	return this.tag.getTag(key);
    }

    @Override
    public byte getTagType(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getTagType(key);
    }

    @Override
    public byte getByte(String key)
    {
    	this.recordKey(key);
    	
    	return this.tag.getByte(key);
    }

    @Override
    public short getShort(String key)
    {
    	this.recordKey(key);
    	
    	return this.tag.getShort(key);
    }

    @Override
    public int getInteger(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getInteger(key);
    }

    @Override
    public long getLong(String key)
    {
    	this.recordKey(key);
    	
    	return this.tag.getLong(key);
    }

    @Override
    public float getFloat(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getFloat(key);
    }

    @Override
    public double getDouble(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getDouble(key);
    }

    @Override
    public String getString(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getString(key);
    }

    @Override
    public byte[] getByteArray(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getByteArray(key);
    }

    @Override
    public int[] getIntArray(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getIntArray(key);
    }

    @Override
    public NBTTagCompound getCompoundTag(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getCompoundTag(key);
    }

    @Override
    public NBTTagList getTagList(String key, int type)
    {
    	this.recordKey(key);
    	
        return this.tag.getTagList(key, type);
    }

    @Override
    public boolean getBoolean(String key)
    {
    	this.recordKey(key);
    	
        return this.tag.getBoolean(key);
    }
    
}
