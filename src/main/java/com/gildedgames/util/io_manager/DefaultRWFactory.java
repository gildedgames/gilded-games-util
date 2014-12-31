package com.gildedgames.util.io_manager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.Input;
import com.gildedgames.util.io_manager.io.Output;

public class DefaultRWFactory<FILE extends IOFile> implements IReaderWriterFactory<FILE, Input, Output>
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

}
