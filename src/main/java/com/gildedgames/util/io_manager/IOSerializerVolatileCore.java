package com.gildedgames.util.io_manager;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;
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
	public <T extends IO<I, ?>, I, FILE extends IOFile<I, ?>> T read(I input, IOFactory<FILE, I, ?> ioFactory)
	{
		return null;
	}

	@Override
	public <T extends IO<I, ?>, I, FILE extends IOFile<I, ?>> T read(I input, IOFactory<FILE, I, ?> ioFactory, IConstructor objectConstructor)
	{
		return null;
	}

	@Override
	public <T extends IO<?, O>, O, FILE extends IOFile<?, O>> void write(O output, IOFactory<FILE, ?, O> ioFactory, T objectToWrite)
	{
		
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException
	{
		return null;
	}

}
