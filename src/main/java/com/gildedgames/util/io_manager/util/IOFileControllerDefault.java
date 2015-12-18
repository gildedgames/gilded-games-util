package com.gildedgames.util.io_manager.util;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOData;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.overhead.ByteChunkPool;
import com.gildedgames.util.io_manager.overhead.IOFileController;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IOFileControllerDefault implements IOFileController
{
	private final IOManager manager;

	private static final String DATA_KEY = "IOClassID";

	private final static int BUFFER_SIZE = 8192;

	public IOFileControllerDefault(IOManager manager)
	{
		this.manager = manager;
	}

	@Override
	public IOManager getManager()
	{
		return this.manager;
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
		
		Class<?> classToRead = inputBridge.getSerializedClass(DATA_KEY);

		FILE ioFile = (FILE) this.getManager().getRegistry().create(classToRead, constructors);

		IOSerializer serializer = this.getManager().getSerializer();

		FILE readFile = serializer.readData(chunkPool, ioFile, factory, constructors);

		dataInput.close();

		return readFile;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<I, O> factory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return;
		}
		
		ByteChunkPool chunkPool = new ByteChunkPool(dataInput);
		
		chunkPool.readChunks();

		I input = factory.createInput(chunkPool.getChunk("class"));//TODO: If anything ever goes wrong or sth, lol, the input here isn't used :D

		IOSerializer serializer = this.getManager().getSerializer();

		serializer.readData(chunkPool, ioFile, factory);

		dataInput.close();
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

		Class<?> classToRead = inputBridge.getSerializedClass(DATA_KEY);

		IOSerializer serializer = IOCore.io().getManager(classToRead).getSerializer();

		IOData<I, O> metadata = serializer.readSubData(chunkPool, factory);

		dataInput.close();

		return metadata;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<I, O> factory) throws IOException
	{
		if (file.getParentFile() != null)
		{
			if(!file.getParentFile().mkdirs())
			{
				throw new IOException();
			}
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

		outputBridge.setSerializedClass(DATA_KEY, ioFile.getClass());

		chunkPool.setChunk("class", outputBridge.getBytes());

		IOManager manager = this.getManager();
		IOSerializer serializer = manager.getSerializer();

		serializer.writeData(chunkPool, ioFile, factory);

		chunkPool.writeChunks();
		
		dataOutput.close();
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
