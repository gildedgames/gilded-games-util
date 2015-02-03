package com.gildedgames.util.io_manager.factory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.util.raw.Input;
import com.gildedgames.util.io_manager.util.raw.Output;

public class IOFactoryDefault<FILE extends IOFile<Input, Output>> implements IOFactory<FILE, Input, Output>
{

	private final File baseDirectory;

	public IOFactoryDefault(File baseDirectory)
	{
		this.baseDirectory = baseDirectory;
	}

	@Override
	public File getFileFromName(FILE data, String name)
	{
		return new File(this.baseDirectory.getAbsolutePath() + File.separator + name);
	}

	@Override
	public Input getInput(DataInputStream input)
	{
		return new Input(input);
	}

	@Override
	public Output getOutput(DataOutputStream output)
	{
		return new Output(output);
	}

	@Override
	public Class<?> getSerializedClass(String key, Input input)
	{
		return this.readSerializedClass(input);
	}

	@Override
	public void setSerializedClass(String key, Output output, Class<?> classToWrite)
	{
		this.writeSerializedClass(output, classToWrite);
	}

	@Override
	public Class<?> readSerializedClass(Input input)
	{
		try
		{
			String registryID = input.readUTF();
			int classID = input.readInt();

			IOManager manager = IOCore.io().getManager(registryID);

			return manager.getRegistry().getClass(registryID, classID);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void writeSerializedClass(Output output, Class<?> classToWrite)
	{
		IOManager manager = IOCore.io().getManager(classToWrite);

		int classID = manager.getRegistry().getID(classToWrite);

		try
		{
			output.writeUTF(manager.getID());
			output.writeInt(classID);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void preReading(FILE data, File from, Input input)
	{
	}

	@Override
	public void preReadingMetadata(IOFileMetadata<Input, Output> metadata, File from, Input reader)
	{
	}

	@Override
	public void finishWriting(DataOutputStream input, Output output)
	{
	}

	@Override
	public Input convertToInput(Output output)
	{
		DataOutputStream stream = output.getStream();
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(stream.toString().getBytes()));
		return new Input(input);
	}

}
