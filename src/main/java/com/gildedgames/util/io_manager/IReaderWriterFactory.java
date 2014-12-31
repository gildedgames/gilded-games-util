package com.gildedgames.util.io_manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;

public interface IReaderWriterFactory<FILE extends IOFile, I extends IReader, O extends IWriter>
{
	
	public I getReader(DataInputStream input, IOManager manager);

	public O getWriter(DataOutputStream output, IOManager manager);

	public File getFileFromName(FILE data, String name);
	
}
