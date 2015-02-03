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

import org.apache.commons.lang3.NotImplementedException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IOSerializer;

public class IOSerializerCore implements IOSerializer
{

	private final static int BUFFER_SIZE = 8192;

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

		I input = ioFactory.getInput(dataInput);

		Class<?> classToRead = ioFactory.getSerializedClass("IOClassID", input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializer serializer = manager.getSerializer();

		return serializer.readFile(file, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor... constructor) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return null;
		}

		I input = ioFactory.getInput(dataInput);

		Class<?> classToRead = ioFactory.getSerializedClass("IOClassID", input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializer serializer = manager.getSerializer();

		return serializer.readFile(file, ioFactory, constructor);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);

		if (dataInput == null)
		{
			return;
		}

		I input = ioFactory.getInput(dataInput);

		Class<?> classToRead = ioFactory.getSerializedClass("IOClassID", input);

		IOManager manager = IOCore.io().getManager(classToRead);
		IOSerializer serializer = manager.getSerializer();

		serializer.readFile(file, ioFile, ioFactory);
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

		O output = ioFactory.getOutput(dataOutput);

		ioFactory.setSerializedClass("IOClassID", output, ioFile.getClass());

		IOManager manager = IOCore.io().getManager(ioFile.getClass());
		IOSerializer serializer = manager.getSerializer();

		serializer.writeFile(file, ioFile, ioFactory);
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

	@Override
	public <I, O> IOFileMetadata<I, O> readFileMetadata(File file, IOFactory<?, I, O> ioFactory) throws IOException
	{
		throw new NotImplementedException("adsf");
	}

}
