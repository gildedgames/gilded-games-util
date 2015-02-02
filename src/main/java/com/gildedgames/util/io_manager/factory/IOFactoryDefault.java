package com.gildedgames.util.io_manager.factory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IORegistry;
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
	public Input getInput(DataInputStream input, IORegistry registry)
	{
		return new Input(registry, input);
	}

	@Override
	public Output getOutput(DataOutputStream output, IORegistry registry)
	{
		return new Output(registry, output);
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

}
