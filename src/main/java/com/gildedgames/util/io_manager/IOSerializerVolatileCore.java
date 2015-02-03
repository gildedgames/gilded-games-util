package com.gildedgames.util.io_manager;

import java.io.IOException;
import java.util.List;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOSerializerVolatileCore implements IOSerializerVolatile
{
	private List<IOManager> managers;

	protected IOSerializerVolatileCore(List<IOManager> managers)
	{
		this.managers = managers;
	}

	@Override
	public IORegistry getParentRegistry()
	{
		return IOCore.io();
	}

	@Override
	public <T extends IO<I, ?>, I, FILE extends IOFile<I, ?>> T read(I input, IOFactory<FILE, I, ?> ioFactory)
	{
		IOManager manager = this.managers.get(0);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();
		
		return serializer.read(input, ioFactory);
	}

	@Override
	public <T extends IO<I, ?>, I, FILE extends IOFile<I, ?>> T read(I input, IOFactory<FILE, I, ?> ioFactory, IConstructor objectConstructor)
	{
		IOManager manager = this.managers.get(0);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();
		
		return serializer.read(input, ioFactory, objectConstructor);
	}

	@Override
	public <T extends IO<?, O>, O, FILE extends IOFile<?, O>> void write(O output, IOFactory<FILE, ?, O> ioFactory, T objectToWrite)
	{
		IOManager manager = this.managers.get(0);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();
		
		serializer.write(output, ioFactory, objectToWrite);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException
	{
		IOManager manager = this.managers.get(0);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();
		
		return serializer.clone(input, output, objectToClone);
	}

}
