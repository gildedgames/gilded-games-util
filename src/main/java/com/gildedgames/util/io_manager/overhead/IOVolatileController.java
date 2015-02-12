package com.gildedgames.util.io_manager.overhead;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;

public interface IOVolatileController
{

	IOManager getManager();
	
	<OBJ extends IO<I, ?>, I> OBJ get(String key, I input, IOFactory<I, ?> ioFactory, IConstructor... objectConstructor);

	<OBJ extends IO<?, O>, O> void set(String key, O output, IOFactory<?, O> ioFactory, OBJ objectToWrite);

	<OBJ extends IO<I, O>, I, O> OBJ clone(IOFactory<I, O> factory, OBJ objectToClone) throws IOException;

}
