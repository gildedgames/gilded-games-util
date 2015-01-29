package com.gildedgames.util.io_manager.factory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;

public interface IReaderWriterFactory<FILE extends IOFile<I, O>, I, O>
{

	I getReader(DataInputStream input, IOManager manager);

	O getWriter(DataOutputStream output, IOManager manager);

	File getFileFromName(FILE data, String name);

	void preReading(FILE data, File from, I reader);

	void preReadingMetadata(IOFileMetadata<I, O> metadata, File from, I reader);

	void finishWriting(DataOutputStream output, O writer);

}
