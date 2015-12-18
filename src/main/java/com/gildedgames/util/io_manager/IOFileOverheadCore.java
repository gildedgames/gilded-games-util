package com.gildedgames.util.io_manager;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.IOObserver;
import com.gildedgames.util.io_manager.io.IOData;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.overhead.ByteChunkPool;
import com.gildedgames.util.io_manager.overhead.IOFileController;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IOFileOverheadCore implements IOFileController
{

	private final static int BUFFER_SIZE = 8192;

	private static final String DATA_KEY = "IOClassID";

	protected IOFileOverheadCore()
	{

	}

	@Override
	public IOManager getManager()
	{
		return IOCore.io();
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<I, O> factory, IConstructor... constructors) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		ByteChunkPool chunkPool = new ByteChunkPool(dataInput);

		chunkPool.readChunks();

		I input = factory.createInput(chunkPool.getChunk("class"));

		IOBridge inputBridge = factory.createInputBridge(input);

		Class<?> classToRead = inputBridge.getSerializedClass(IOFileOverheadCore.DATA_KEY);

		FILE ioFile = (FILE) IOCore.io().create(classToRead, constructors);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializer serializer = manager.getSerializer();

		for (IOObserver<I, O> observer : factory.getObservers())
		{
			if (observer != null)
			{
				observer.preReading(ioFile, file, input);
			}
		}

		FILE readFile = serializer.readData(chunkPool, ioFile, factory, constructors);

		dataInput.close();

		return readFile;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return;
		}

		ByteChunkPool chunkPool = new ByteChunkPool(dataInput);

		chunkPool.readChunks();

		I input = ioFactory.createInput(chunkPool.getChunk("class"));

		IOManager manager = IOCore.io().getManager(ioFile.getClass());
		IOSerializer serializer = manager.getSerializer();

		for (IOObserver<I, O> observer : ioFactory.getObservers())
		{
			if (observer != null)
			{
				observer.preReading(ioFile, file, input);
			}
		}

		serializer.readData(chunkPool, ioFile, ioFactory);

		dataInput.close();
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<I, O> factory) throws IOException
	{
		if (file.getParentFile() != null)
		{
			file.getParentFile().mkdirs();
		}

		if (!file.exists())
		{
			if(!file.createNewFile())
			{
				throw new IOException();
			}
		}

		final FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(fileOutputStream), BUFFER_SIZE);

		final DataOutputStream dataOutput = new DataOutputStream(bufferedOutputStream);

		ByteChunkPool chunkPool = new ByteChunkPool(dataOutput);

		O output = factory.createOutput();

		IOBridge outputBridge = factory.createOutputBridge(output);

		outputBridge.setSerializedClass(IOFileOverheadCore.DATA_KEY, ioFile.getClass());

		chunkPool.setChunk("class", outputBridge.getBytes());

		IOManager manager = IOCore.io().getManager(ioFile.getClass());
		IOSerializer serializer = manager.getSerializer();

		serializer.writeData(chunkPool, ioFile, factory);

		chunkPool.writeChunks();

		dataOutput.close();
	}

	@Override
	public <I, O> IOData<I, O> readFileMetadata(File file, IOFactory<I, O> factory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		ByteChunkPool chunkPool = new ByteChunkPool(dataInput);

		chunkPool.readChunks();

		I input = factory.createInput(chunkPool.getChunk("class"));

		IOBridge inputBridge = factory.createInputBridge(input);

		Class<?> classToRead = inputBridge.getSerializedClass(IOFileOverheadCore.DATA_KEY);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializer serializer = manager.getSerializer();

		IOData<I, O> metadata = serializer.readSubData(chunkPool, factory);

		dataInput.close();

		return metadata;
	}

	private DataInputStream createDataInput(File file) throws IOException
	{
		if (!file.exists())
		{
			return null;
		}

		final FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(new GZIPInputStream(fileInputStream), BUFFER_SIZE);

		return new DataInputStream(bufferedInputStream);
	}

}
