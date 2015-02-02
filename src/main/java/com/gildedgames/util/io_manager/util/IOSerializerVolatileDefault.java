package com.gildedgames.util.io_manager.util;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOSerializerVolatileDefault implements IOSerializerVolatile
{

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();
	
	private IORegistry parentRegistry;
	
	public IOSerializerVolatileDefault(IORegistry parentRegistry)
	{
		this.parentRegistry = parentRegistry;
	}
	
	@Override
	public IORegistry getParentRegistry()
	{
		return this.parentRegistry;
	}
	
	@Override
	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom)
	{
		return this.read(input, classToReadFrom, defaultConstructor);
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom, IConstructor objectConstructor)
	{
		final T io = this.cast(this.getParentRegistry().create(classToReadFrom, objectConstructor));

		io.read(input);

		return io;
	}

	@Override
	public <T extends IO<?, O>, O> void write(O output, T objectToWrite)
	{
		objectToWrite.write(output);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException
	{
		final T clone = this.cast(this.getParentRegistry().create(objectToClone.getClass(), defaultConstructor));

		objectToClone.write(output);

		clone.read(input);

		return clone;
	}

	@SuppressWarnings("unchecked")
	private <T> T cast(Object object)
	{
		return (T) object;
	}
	
}
