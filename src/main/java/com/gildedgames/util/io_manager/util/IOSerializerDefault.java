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

import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.google.common.base.Optional;

public class IOSerializerDefault implements IOSerializer
{

	private final static int BUFFER_SIZE = 8192;

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private final IOManager parentManager;

	private static String dataKey = "fileClassq", metadataKey = "metaClassq";

	public IOSerializerDefault(IOManager parentManager)
	{
		this.parentManager = parentManager;
	}

	@Override
	public IOManager getManager()
	{
		return this.parentManager;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		return this.readFile(file, ioFactory, defaultConstructor);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor... constructors) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		IOFileMetadata<I, O> metadata = this.readMetadata(file, dataInput, ioFactory);

		I input = ioFactory.getInput(dataInput);
		Class<?> clazz = ioFactory.getSerializedClass(dataKey, input);
		@SuppressWarnings("unchecked")
		FILE ioFile = (FILE) this.getManager().getRegistry().create(clazz, constructors);

		ioFactory.preReading(ioFile, file, input);
		ioFile.read(input);

		ioFile.setMetadata(metadata);

		dataInput.close();

		return ioFile;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		if (file.getParentFile() != null)
		{
			file.getParentFile().mkdirs();
		}

		if (!file.exists())
		{
			file.createNewFile();
		}

		final FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(fileOutputStream), BUFFER_SIZE);

		final DataOutputStream dataOutput = new DataOutputStream(bufferedOutputStream);

		Optional<IOFileMetadata<I, O>> metadata = ioFile.getMetadata();

		int i = 0;
		while (metadata != null && metadata.isPresent())
		{
			IOFileMetadata<I, O> metadataFile = metadata.get();

			dataOutput.writeBoolean(true);

			final O output = ioFactory.getOutput(dataOutput);
			ioFactory.setSerializedClass(metadataKey + i, output, metadataFile.getClass());
			metadataFile.write(output);
			ioFactory.finishWriting(dataOutput, output);
			i++;

			metadata = metadataFile.getMetadata();
		}

		dataOutput.writeBoolean(false);//Not metadata

		final O output = ioFactory.getOutput(dataOutput);
		ioFactory.setSerializedClass(dataKey, output, ioFile.getDataClass());

		ioFile.write(output);

		ioFactory.finishWriting(dataOutput, output);

		dataOutput.close();
	}

	@Override
	public <I, O> IOFileMetadata<I, O> readFileMetadata(File file, IOFactory<?, I, O> ioFactory) throws IOException
	{
		DataInputStream dataInput = this.createDataInput(file);
		if (dataInput == null)
		{
			return null;
		}
		IOFileMetadata<I, O> metadata = this.readMetadata(file, dataInput, ioFactory);
		dataInput.close();
		return metadata;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return;
		}

		IOFileMetadata<I, O> metadata = this.readMetadata(file, dataInput, ioFactory);
		ioFile.setMetadata(metadata);

		I input = ioFactory.getInput(dataInput);
		ioFactory.getSerializedClass(dataKey, input);

		ioFactory.preReading(ioFile, file, input);
		ioFile.read(input);

		dataInput.close();
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

	private <I, O, FILE extends IOFile<I, O>> IOFileMetadata<I, O> readMetadata(File file, DataInputStream dataInput, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		boolean isMetadata = dataInput.readBoolean();
		IOFileMetadata<I, O> readMetadata = null;

		int i = 0;
		while (isMetadata)//Keep reading metadata
		{
			final I input = ioFactory.getInput(dataInput);

			Class<?> clazz = ioFactory.getSerializedClass(metadataKey + i, input);
			@SuppressWarnings("unchecked")
			IOFileMetadata<I, O> ioFile = (IOFileMetadata<I, O>) this.getManager().getRegistry().create(clazz, defaultConstructor);

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
