package com.gildedgames.util.core.nbt.util;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.network.PacketBuffer;

public class NBTUtil
{
	
	public static byte[] getBytesFrom(NBTBase nbt)
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(byteStream);
		
		try
		{
			if (nbt == null)
			{
				stream.writeBoolean(false);
			}
			else
			{
				stream.writeBoolean(true);
				writeNBTBase(nbt, stream);
			}
			
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
	
    public static void writeNBTBaseToBuffer(PacketBuffer buf, NBTBase nbt)
    {
        if (nbt == null)
        {
            buf.writeByte(0);
        }
        else
        {
            try
            {
                writeNBTBase(nbt, new ByteBufOutputStream(buf));
            }
            catch (IOException ioexception)
            {
                throw new EncoderException(ioexception);
            }
        }
    }

    public static NBTBase readNBTBaseFromBuffer(PacketBuffer buf) throws IOException
    {
        int i = buf.readerIndex();
        byte b0 = buf.readByte();

        if (b0 == 0)
        {
            return null;
        }
        else
        {
            buf.readerIndex(i);
            return readNBTBase(new ByteBufInputStream(buf), 0, new NBTSizeTracker(2097152L));
        }
    }
    
    private static void writeNBTBase(NBTBase nbt, DataOutput output) throws IOException
    {
        output.writeByte(nbt.getId());

        if (nbt.getId() != 0)
        {
            output.writeUTF("");

			try
			{
				Method write = nbt.getClass().getDeclaredMethod("write");
				
				write.setAccessible(true);
	            
	            write.invoke(output);
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
        }
    }
    
    private static NBTBase readNBTBase(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException
    {
        byte b0 = input.readByte();

        if (b0 == 0)
        {
            return new NBTTagEnd();
        }
        else
        {
        	try
			{
				Method createNewByType = NBTBase.class.getDeclaredMethod("createNewByType");
				
				createNewByType.setAccessible(true);
	            
	            input.readUTF();
	            
	            NBTBase nbtbase = (NBTBase) createNewByType.invoke(b0);

	            Method read = nbtbase.getClass().getDeclaredMethod("read");
				
				read.setAccessible(true);
				
				read.invoke(nbtbase, input, depth, sizeTracker);
				
				return nbtbase;
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
        }
        
        return null;
    }

}
