package com.gildedgames.util.io_manager.overhead;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;

import java.io.IOException;

public interface IOVolatileController
{

	IOManager getManager();

	<OBJ extends IO<I, O>, I, O> OBJ get(String key, I input, IOFactory<I, O> ioFactory, IConstructor... objectConstructor);

	<OBJ extends IO<I, O>, I, O> void set(String key, O output, IOFactory<I, O> ioFactory, OBJ objectToWrite);

	<OBJ extends IO<I, O>, I, O> OBJ clone(IOFactory<I, O> factory, OBJ objectToClone) throws IOException;

}
