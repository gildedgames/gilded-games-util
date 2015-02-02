package com.gildedgames.util.io_manager;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOSerializerVolatileCore implements IOSerializerVolatile
{

	protected IOSerializerVolatileCore()
	{
		
	}

	@Override
	public IORegistry getParentRegistry()
	{
		return IOCore.io();
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom)
	{
		return null;
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom, IConstructor objectConstructor)
	{
		return null;
	}

	@Override
	public <T extends IO<?, O>, O> void write(O output, T objectToWrite)
	{
		
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException
	{
		return null;
	}
	
}
