package com.gildedgames.util.io_manager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializerExternal;
import com.google.common.base.Optional;

public class IOSerializerExternalDefault implements IOSerializerExternal
{

	private final static int BUFFER_SIZE = 8192;

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private final IOManager parentManager;

	private final static String METADATA_KEY = "metaClassq";

	public IOSerializerExternalDefault(IOManager parentManager)
	{
		this.parentManager = parentManager;
	}

	@Override
	public IOManager getManager()
	{
		return this.parentManager;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(DataInputStream inputStream, FILE ioFile, File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		return this.readFile(inputStream, ioFile, file, ioFactory, defaultConstructor);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(DataInputStream inputStream, FILE ioFile, File file, IOFactory<FILE, I, O> ioFactory, IConstructor... constructors) throws IOException
	{
		IOFileMetadata<I, O> metadata = this.readMetadata(file, inputStream, ioFactory);
		
		ioFile.setMetadata(metadata);

		I input = ioFactory.getInput(IOUtil.readBytes(inputStream));

		ioFile.read(input);
		
		return ioFile;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(DataOutputStream outputStream, File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		Optional<IOFileMetadata<I, O>> metadata = ioFile.getMetadata();

		int i = 0;
		while (metadata != null && metadata.isPresent())
		{
			IOFileMetadata<I, O> metadataFile = metadata.get();

			outputStream.writeBoolean(true);

			final O output = ioFactory.getOutput();
			ioFactory.setSerializedClass(METADATA_KEY + i, output, metadataFile.getClass());
			metadataFile.write(output);
			IOUtil.writeBytes(output, outputStream, ioFactory);
			i++;

			metadata = metadataFile.getMetadata();
		}

		outputStream.writeBoolean(false);//Not metadata

		final O output = ioFactory.getOutput();

		ioFile.write(output);

		IOUtil.writeBytes(output, outputStream, ioFactory);
	}

	@Override
	public <I, O> IOFileMetadata<I, O> readFileMetadata(DataInputStream inputStream, Class<?> classToRead, File file, IOFactory<?, I, O> ioFactory) throws IOException
	{
		IOFileMetadata<I, O> metadata = this.readMetadata(file, inputStream, ioFactory);
		
		return metadata;
	}

	private <I, O, FILE extends IOFile<I, O>> IOFileMetadata<I, O> readMetadata(File file, DataInputStream dataInput, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		boolean isMetadata = dataInput.readBoolean();
		IOFileMetadata<I, O> readMetadata = null;

		int i = 0;
		
		while (isMetadata)//Keep reading metadata
		{
			final I input = ioFactory.getInput(IOUtil.readBytes(dataInput));

			Class<?> clazz = ioFactory.getSerializedClass(METADATA_KEY + i, input);
			@SuppressWarnings("unchecked")
			IOFileMetadata<I, O> ioFile = (IOFileMetadata<I, O>) IOCore.io().create(clazz, defaultConstructor);

			if (readMetadata != null)
			{
				readMetadata.setMetadata(ioFile);
			}
			ioFactory.preReadingMetadata(ioFile, file, input);

			ioFile.read(input);
			ioFile.setFileLocation(file);

			readMetadata = ioFile;
			isMetadata = dataInput.readBoolean();
		}

		return readMetadata;
	}
	
}
