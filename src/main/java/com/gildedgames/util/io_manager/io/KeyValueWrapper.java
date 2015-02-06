package com.gildedgames.util.io_manager.io;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ReportedException;

public interface KeyValueWrapper
{

	void setBoolean(String key, boolean value);

    void setByte(String key, byte value);

    void setShort(String key, short value);

    void setInteger(String key, int value);

    void setLong(String key, long value);

    void setFloat(String key, float value);

    void setDouble(String key, double value);

    void setString(String key, String value);

    void setByteArray(String key, byte[] value);

    void setIntArray(String key, int[] value);
    
    byte getByte(String key);
    
    short getShort(String key);

    int getInteger(String key);

    long getLong(String key);

    float getFloat(String key);
    
    double getDouble(String key);

    String getString(String key);

    byte[] getByteArray(String key);

    int[] getIntArray(String key);

    boolean getBoolean(String key);
	
}