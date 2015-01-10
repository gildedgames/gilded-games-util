package com.gildedgames.util.io_manager.factory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.io.Input;
import com.gildedgames.util.io_manager.io.Output;

public class DefaultRWFactory<FILE extends IOFile<Input, Output>> implements IReaderWriterFactory<FILE, Input, Output>
{

	private final File baseDirectory;

	public DefaultRWFactory(File baseDirectory)
	{
		this.baseDirectory = baseDirectory;
	}

	@Override
	public File getFileFromName(FILE data, String name)
	{
		return new File(this.baseDirectory.getAbsolutePath() + File.separator + name);
	}

	@Override
	public Input getReader(DataInputStream input, IOManager manager)
	{
		return new Input(manager, input);
	}

	@Override
	public Output getWriter(DataOutputStream output, IOManager manager)
	{
		return new Output(manager, output);
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
