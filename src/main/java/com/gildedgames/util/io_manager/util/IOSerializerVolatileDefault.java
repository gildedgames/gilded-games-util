package com.gildedgames.util.io_manager.util;

import java.io.DataOutputStream;
import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class IOSerializerVolatileDefault implements IOSerializerVolatile
{

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private IOManager manager;

	public IOSerializerVolatileDefault(IOManager manager)
	{
		this.manager = manager;
	}

	@Override
	public IOManager getManager()
	{
		return this.manager;
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory)
	{
		return this.read(input, ioFactory, defaultConstructor);
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory, IConstructor objectConstructor)
	{
		Class<?> classToReadFrom = ioFactory.readSerializedClass(input);

		final T io = this.cast(this.getManager().getRegistry().create(classToReadFrom, objectConstructor));

		io.read(input);

		return io;
	}

	@Override
	public <T extends IO<?, O>, O> void write(O output, IOFactory<?, ?, O> ioFactory, T objectToWrite)
	{
		ioFactory.writeSerializedClass(output, objectToWrite.getClass());

		objectToWrite.write(output);
	}

	@Override
	public <T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory)
	{
		return this.get(key, input, ioFactory, defaultConstructor);
	}

	@Override
	public <T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory, IConstructor objectConstructor)
	{
		Class<?> classToReadFrom = ioFactory.getSerializedClass(key, input);

		final T io = this.cast(this.getManager().getRegistry().create(classToReadFrom, objectConstructor));

		io.read(input);

		return io;
	}

	@Override
	public <T extends IO<?, O>, O> void set(String key, O output, IOFactory<?, ?, O> ioFactory, T objectToWrite)
	{
		ioFactory.setSerializedClass(key, output, objectToWrite.getClass());

		objectToWrite.write(output);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(IOFactory<?, I, O> factory, T objectToClone) throws IOException
	{
		final T clone = this.cast(this.getManager().getRegistry().create(objectToClone.getClass(), defaultConstructor));

		O output = factory.getOutput(new DataOutputStream(new ByteOutputStream()));
		objectToClone.write(output);
		I input = factory.convertToInput(output);

		clone.read(input);

		return clone;
	}

	@SuppressWarnings("unchecked")
	private <T> T cast(Object object)
	{
		return (T) object;
	}

}
