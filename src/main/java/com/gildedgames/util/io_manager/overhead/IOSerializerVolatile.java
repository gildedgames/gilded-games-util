package com.gildedgames.util.io_manager.overhead;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;

public interface IOSerializerVolatile
{
	
	IORegistry getParentRegistry();

	<T extends IO<I, ?>, I, FILE extends IOFile<I, ?>> T read(I input, IOFactory<FILE, I, ?> ioFactory);

	<T extends IO<I, ?>, I, FILE extends IOFile<I, ?>> T read(I input, IOFactory<FILE, I, ?> ioFactory, IConstructor objectConstructor);
	
	<T extends IO<?, O>, O, FILE extends IOFile<?, O>> void write(O output, IOFactory<FILE, ?, O> ioFactory, T objectToWrite);

	<T extends IO<I, O>, I, O> T clone(I input, O output, T objectToClone) throws IOException;
	
}