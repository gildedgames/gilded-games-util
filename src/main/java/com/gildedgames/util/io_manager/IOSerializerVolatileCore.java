package com.gildedgames.util.io_manager;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOSerializerVolatileCore implements IOSerializerVolatile
{

	protected IOSerializerVolatileCore()
	{

	}

	@Override
	public IOManager getManager()
	{
		return IOCore.io();
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory)
	{
		Class<?> classToRead = ioFactory.readSerializedClass(input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		return serializer.read(input, ioFactory);
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory, IConstructor... objectConstructor)
	{
		Class<?> classToRead = ioFactory.readSerializedClass(input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		return serializer.read(input, ioFactory, objectConstructor);
	}

	@Override
	public <T extends IO<?, O>, O> void write(O output, IOFactory<?, ?, O> ioFactory, T objectToWrite)
	{
		ioFactory.writeSerializedClass(output, objectToWrite.getClass());

		IOManager manager = IOCore.io().getManager(objectToWrite.getClass());
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		serializer.write(output, ioFactory, objectToWrite);
	}

	@Override
	public <T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory)
	{
		Class<?> classToRead = ioFactory.readSerializedClass(input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		return serializer.get(key, input, ioFactory);
	}

	@Override
	public <T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory, IConstructor... objectConstructor)
	{
		Class<?> classToRead = ioFactory.readSerializedClass(input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		return serializer.get(key, input, ioFactory, objectConstructor);
	}

	@Override
	public <T extends IO<?, O>, O> void set(String key, O output, IOFactory<?, ?, O> ioFactory, T objectToWrite)
	{
		ioFactory.writeSerializedClass(output, objectToWrite.getClass());

		IOManager manager = IOCore.io().getManager(objectToWrite.getClass());
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		serializer.set(key, output, ioFactory, objectToWrite);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(IOFactory<?, I, O> factory, T objectToClone) throws IOException
	{
		IOManager manager = IOCore.io().getManager(objectToClone.getClass());
		IOSerializerVolatile serializer = manager.getVolatileSerializer();

		return serializer.clone(factory, objectToClone);
	}

}
