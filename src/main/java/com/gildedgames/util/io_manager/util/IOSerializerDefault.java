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

import javax.print.DocFlavor.READER;

import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.google.common.base.Optional;

public class IOSerializerDefault implements IOSerializer
{
	
	private final static int BUFFER_SIZE = 8192;

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();
	
	private final IORegistry parentRegistry;
	
	public IOSerializerDefault(IORegistry parentRegistry)
	{
		this.parentRegistry = parentRegistry;
	}
	
	@Override
	public IORegistry getParentRegistry()
	{
		return this.parentRegistry;
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		return this.readFile(file, ioFactory, defaultConstructor);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor constructor) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);
		
		if (dataInput == null)
		{
			return null;
		}

		IOFileMetadata<I, O> metadata = this.readMetadata(file, dataInput, ioFactory);
		
		@SuppressWarnings("unchecked")
		FILE ioFile = (FILE) this.getParentRegistry().create(this.getParentRegistry().getRegistryID(), dataInput.readInt(), constructor);
		
		ioFile.setMetadata(metadata);
		
		this.readData(file, ioFile, dataInput, ioFactory);//Read final data
		
		dataInput.close();
		
		return ioFile;
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
		dataInput.readInt();//We're reading it into an already existing FILE object, meaning we don't need to construct it and can disregard the written int
		this.readData(file, ioFile, dataInput, ioFactory);

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

		Optional<IOFileMetadata<I, O>> metadata = ioFile.getMetadata();
		
		while (metadata != null && metadata.isPresent())
		{
			IOFileMetadata<I, O> metadataFile = metadata.get();

			dataOutput.writeBoolean(true);
			dataOutput.writeInt(this.getParentRegistry().getID(metadataFile.getClass()));

			final O output = ioFactory.getOutput(dataOutput);
			metadataFile.write(output);
			ioFactory.finishWriting(dataOutput, output);

			metadata = metadataFile.getMetadata();
		}

		dataOutput.writeBoolean(false);//Not metadata
		dataOutput.writeInt(this.getParentRegistry().getID(ioFile.getDataClass()));

		final O output = ioFactory.getOutput(dataOutput);

		ioFile.write(output);

		ioFactory.finishWriting(dataOutput, output);

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
	
	private <I, O, FILE extends IOFile<I, O>> IOFileMetadata<I, O> readMetadata(File file, DataInputStream dataInput, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		boolean isMetadata = dataInput.readBoolean();
		IOFileMetadata<I, O> readMetadata = null;

		while (isMetadata)//Keep reading metadata
		{
			int ioKey = dataInput.readInt();
			
			@SuppressWarnings("unchecked")
			IOFileMetadata<I, O> ioFile = (IOFileMetadata<I, O>) this.getParentRegistry().create(this.getParentRegistry().getRegistryID(), ioKey, defaultConstructor);
			
			if (readMetadata != null)
			{
				readMetadata.setMetadata(ioFile);
			}
			
			this.readMetadata(file, ioFile, dataInput, ioFactory);
			
			readMetadata = ioFile;
			isMetadata = dataInput.readBoolean();
		}

		return readMetadata;
	}
	
	private <I, O, FILE extends IOFile<I, O>> void readData(File file, FILE ioFile, DataInputStream dataInput, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		final I input = ioFactory.getInput(dataInput);
		ioFactory.preReading(ioFile, file, input);

		ioFile.read(input);
	}

	private <I, O, FILE extends IOFile<I, O>> void readMetadata(File file, IOFileMetadata<I, O> ioFile, DataInputStream dataInput, IOFactory<FILE, I, O> rwFac) throws IOException
	{
		final I input = rwFac.getInput(dataInput);
		rwFac.preReadingMetadata(ioFile, file, input);

		ioFile.read(input);
		ioFile.setFileLocation(file);
	}

}