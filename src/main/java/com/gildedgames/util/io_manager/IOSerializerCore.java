package com.gildedgames.util.io_manager;

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

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerInternal;
import com.gildedgames.util.io_manager.util.IOUtil;

public class IOSerializerCore implements IOSerializer
{

	private final static int BUFFER_SIZE = 8192;

	private static final String DATA_KEY = "IOClassID";

	protected IOSerializerCore()
	{

	}

	@Override
	public IOManager getManager()
	{
		return IOCore.io();
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		I input = ioFactory.createInput(IOUtil.readBytes(dataInput));

		Class<?> classToRead = ioFactory.getSerializedClass(IOSerializerCore.DATA_KEY, input);

		FILE ioFile = (FILE) IOCore.io().create(classToRead);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerInternal serializer = manager.getInternalSerializer();

		ioFactory.preReading(ioFile, file, input);

		FILE readFile = serializer.readFile(dataInput, ioFile, file, ioFactory);

		dataInput.close();

		return readFile;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor... constructors) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		I input = ioFactory.createInput(IOUtil.readBytes(dataInput));

		Class<?> classToRead = ioFactory.getSerializedClass(IOSerializerCore.DATA_KEY, input);

		FILE ioFile = (FILE) IOCore.io().create(classToRead, constructors);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerInternal serializer = manager.getInternalSerializer();

		ioFactory.preReading(ioFile, file, input);

		FILE readFile = serializer.readFile(dataInput, ioFile, file, ioFactory, constructors);

		dataInput.close();

		return readFile;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return;
		}

		I input = ioFactory.createInput(IOUtil.readBytes(dataInput));

		IOManager manager = IOCore.io().getManager(ioFile.getClass());
		IOSerializerInternal serializer = manager.getInternalSerializer();

		ioFactory.preReading(ioFile, file, input);

		serializer.readFile(dataInput, ioFile, file, ioFactory);

		dataInput.close();
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

		O output = ioFactory.createOutput();

		ioFactory.setSerializedClass(IOSerializerCore.DATA_KEY, output, ioFile.getClass());

		IOUtil.writeBytes(output, dataOutput, ioFactory);

		IOManager manager = IOCore.io().getManager(ioFile.getClass());
		IOSerializerInternal serializer = manager.getInternalSerializer();

		serializer.writeFile(dataOutput, file, ioFile, ioFactory);

		dataOutput.close();
	}

	@Override
	public <I, O> IOFileMetadata<I, O> readFileMetadata(File file, IOFactory<?, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		I input = ioFactory.createInput(IOUtil.readBytes(dataInput));

		Class<?> classToRead = ioFactory.getSerializedClass(IOSerializerCore.DATA_KEY, input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializerInternal serializer = manager.getInternalSerializer();

		IOFileMetadata<I, O> metadata = serializer.readFileMetadata(dataInput, file, ioFactory);

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
