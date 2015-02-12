package com.gildedgames.util.core.nbt;

import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.util.NBTHelper;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IOManagerNotFoundException;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.IOObserver;
import com.gildedgames.util.io_manager.overhead.IOManager;

public class NBTFactory implements IOFactory<NBTTagCompound, NBTTagCompound>
{

	private int writeIndex, readIndex;
	
	private ArrayList<IOObserver<NBTTagCompound, NBTTagCompound>> observers = new ArrayList<IOObserver<NBTTagCompound, NBTTagCompound>>();

	@Override
	public NBTTagCompound createInput(boolean orderDependent, byte[] reading)
	{
		if (orderDependent)
		{
			return new NBTRaw(this, Unpooled.copiedBuffer(reading));
		}
		
		try
		{
			DataInputStream stream = new DataInputStream(new ByteArrayInputStream(reading));
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
	public NBTTagCompound createOutput(boolean orderDependent)
	{
		if (orderDependent)
		{
			return new NBTRaw(this);
		}
		
		return new NBTTagCompound();
	}
	
	@Override
	public IOBridge createInputBridge(NBTTagCompound input)
	{
		return new NBTBridge(this, input);
	}

	@Override
	public IOBridge createOutputBridge(NBTTagCompound output)
	{
		return new NBTBridge(this, output);
	}

	@Override
	public List<IOObserver<NBTTagCompound, NBTTagCompound>> getObservers()
	{
		return this.observers;
	}

}
