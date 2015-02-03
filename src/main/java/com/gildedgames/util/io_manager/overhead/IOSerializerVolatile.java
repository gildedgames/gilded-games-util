package com.gildedgames.util.io_manager.overhead;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;

public interface IOSerializerVolatile
{

	IOManager getManager();

	<T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory);

	<T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory, IConstructor... objectConstructor);

	<T extends IO<?, O>, O> void write(O output, IOFactory<?, ?, O> ioFactory, T objectToWrite);

	<T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory);

	<T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory, IConstructor... objectConstructor);

	<T extends IO<?, O>, O> void set(String key, O output, IOFactory<?, ?, O> ioFactory, T objectToWrite);

	<T extends IO<I, O>, I, O> T clone(IOFactory<?, I, O> factory, T objectToClone) throws IOException;

}
