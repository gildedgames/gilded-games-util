package com.gildedgames.util.io_manager.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOData;
import com.gildedgames.util.io_manager.overhead.ByteChunkPool;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOVolatileController;

public class IOVolatileControllerDefault implements IOVolatileController
{

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private IOManager manager;

	public IOVolatileControllerDefault(IOManager manager)
	{
		this.manager = manager;
	}

	@Override
	public IOManager getManager()
	{
		return this.manager;
	}

	@Override
	public <T extends IO<I, O>, I, O> T get(String key, I input, IOFactory<I, O> factory, IConstructor... objectConstructors)
	{
		IOBridge inputBridge = factory.createInputBridge(input);

		Class<?> classToReadFrom = inputBridge.getSerializedClass(key);

		final T io = this.cast(this.getManager().getRegistry().create(classToReadFrom, objectConstructors));

		if (io instanceof IOData)
		{
			IOData<I, O> data = (IOData<I, O>) io;

			byte[] array = inputBridge.getByteArray(key + "bytes");

			IOManager manager = IOCore.io().getManager(io.getClass());
			IOSerializer serializer = manager.getSerializer();

			ByteArrayInputStream inputStream = new ByteArrayInputStream(array);
			DataInputStream dataInput = new DataInputStream(inputStream);

			ByteChunkPool chunkPool = new ByteChunkPool(dataInput);

			try
			{
				chunkPool.readChunks();

				serializer.readData(chunkPool, data, factory, objectConstructors);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			byte[] array = inputBridge.getByteArray(key + "bytes");
			I newInput = factory.createInput(array);
			io.read(newInput);
		}

		return io;
	}

	@Override
	public <T extends IO<I, O>, I, O> void set(String key, O output, IOFactory<I, O> factory, T objectToWrite)
	{
		IOBridge outputBridge = factory.createOutputBridge(output);

		outputBridge.setSerializedClass(key, objectToWrite.getClass());

		if (objectToWrite instanceof IOData)
		{
			IOData<I, O> data = (IOData<I, O>) objectToWrite;

			IOManager manager = IOCore.io().getManager(objectToWrite.getClass());
			IOSerializer serializer = manager.getSerializer();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutput = new DataOutputStream(outputStream);

			ByteChunkPool chunkPool = new ByteChunkPool(dataOutput);

			try
			{
				serializer.writeData(chunkPool, data, factory);

				chunkPool.writeChunks();

				outputBridge.setByteArray(key + "bytes", outputStream.toByteArray());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			O newOutput = factory.createOutput();
			objectToWrite.write(newOutput);
			outputBridge.setByteArray(key + "bytes", factory.createOutputBridge(newOutput).getBytes());
		}
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(IOFactory<I, O> factory, T objectToClone) throws IOException
	{
		O output = factory.createOutput();

		this.set("clonedObject", output, factory, objectToClone);

		IOBridge outputBridge = factory.createOutputBridge(output);

		I input = factory.createInput(outputBridge.getBytes());

		T clone = this.get("clonedObject", input, factory);

		return clone;
	}

	@SuppressWarnings("unchecked")
	private <T> T cast(Object object)
	{
		return (T) object;
	}

}
