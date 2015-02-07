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

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerInternal;

public class IOSerializerDefault implements IOSerializer
{
	private final IOManager manager;

	private static final String DATA_KEY = "IOClassID";

	private final static int BUFFER_SIZE = 8192;

	public IOSerializerDefault(IOManager manager)
	{
		this.manager = manager;
	}

	@Override
	public IOManager getManager()
	{
		return this.manager;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		I input = ioFactory.getInput(IOUtil.readBytes(dataInput));

		Class<?> classToRead = ioFactory.getSerializedClass(IOSerializerDefault.DATA_KEY, input);

		FILE ioFile = (FILE) this.getManager().getRegistry().create(classToRead);

		IOManager manager = this.getManager();
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
		I input = ioFactory.getInput(IOUtil.readBytes(dataInput));
		Class<?> classToRead = ioFactory.getSerializedClass(DATA_KEY, input);

		FILE ioFile = (FILE) this.getManager().getRegistry().create(classToRead, constructors);

		IOSerializerInternal serializer = this.getManager().getInternalSerializer();

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

		I input = ioFactory.getInput(IOUtil.readBytes(dataInput));

		IOSerializerInternal serializer = this.getManager().getInternalSerializer();

		ioFactory.preReading(ioFile, file, input);

		serializer.readFile(dataInput, ioFile, file, ioFactory);

		dataInput.close();
	}

	@Override
	public <I, O> IOFileMetadata<I, O> readFileMetadata(File file, IOFactory<?, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		Class<?> classToRead = this.getClassToRead(dataInput, ioFactory);

		IOSerializerInternal serializer = this.getManager().getInternalSerializer();

		IOFileMetadata<I, O> metadata = serializer.readFileMetadata(dataInput, file, ioFactory);

		dataInput.close();

		return metadata;
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

		O output = ioFactory.getOutput();

		ioFactory.setSerializedClass(DATA_KEY, output, ioFile.getClass());

		IOUtil.writeBytes(output, dataOutput, ioFactory);

		IOManager manager = this.getManager();
		IOSerializerInternal serializer = manager.getInternalSerializer();

		serializer.writeFile(dataOutput, file, ioFile, ioFactory);

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

	private <I> Class<?> getClassToRead(DataInputStream stream, IOFactory<?, I, ?> ioFactory) throws IOException
	{
		I input = ioFactory.getInput(IOUtil.readBytes(stream));

		return ioFactory.getSerializedClass(DATA_KEY, input);
	}

}
