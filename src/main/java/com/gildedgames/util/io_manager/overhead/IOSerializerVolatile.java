package com.gildedgames.util.io_manager.overhead;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.io.IO;

public interface IOSerializerVolatile
{
	
	IORegistry getParentRegistry();

	<T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom);

	<T extends IO<I, ?>, I> T read(I input, Class<? extends T> classToReadFrom, IConstructor objectConstructor);
	
	<T extends IO<?, O>, O> void write(O output, T objectToWrite);

	<T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException;
	
}