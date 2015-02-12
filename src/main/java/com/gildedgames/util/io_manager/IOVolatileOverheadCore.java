package com.gildedgames.util.io_manager;

import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOVolatileController;

public class IOVolatileOverheadCore implements IOVolatileController
{

	protected IOVolatileOverheadCore()
	{

	}

	@Override
	public IOManager getManager()
	{
		return IOCore.io();
	}

	@Override
	public <T extends IO<I, O>, I, O> T get(String key, I input, IOFactory<I, O> factory, IConstructor... objectConstructor)
	{
		IOBridge inputBridge = factory.createInputBridge(input);

		Class<?> classToRead = inputBridge.getSerializedClass(key);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOVolatileController serializer = manager.getVolatileController();

		return serializer.get(key, input, factory, objectConstructor);
	}

	@Override
	public <T extends IO<I, O>, I, O> void set(String key, O output, IOFactory<I, O> factory, T objectToWrite)
	{
		IOBridge outputBridge = factory.createOutputBridge(output);

		outputBridge.setSerializedClass(key, objectToWrite.getClass());

		IOManager manager = IOCore.io().getManager(objectToWrite.getClass());
		IOVolatileController serializer = manager.getVolatileController();

		serializer.set(key, output, factory, objectToWrite);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(IOFactory<I, O> factory, T objectToClone) throws IOException
	{
		IOManager manager = IOCore.io().getManager(objectToClone.getClass());
		IOVolatileController serializer = manager.getVolatileController();

		return serializer.clone(factory, objectToClone);
	}

}
