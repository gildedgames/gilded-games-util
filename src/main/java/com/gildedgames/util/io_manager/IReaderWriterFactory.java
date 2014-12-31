package com.gildedgames.util.io_manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;

public interface IReaderWriterFactory<FILE extends IOFile<I, O>, I, O>
{

	I getReader(DataInputStream input, IOManager manager);

	O getWriter(DataOutputStream output, IOManager manager);

	File getFileFromName(FILE data, String name);

	void preReading(FILE data, File from, I reader);

	void finishWriting(DataOutputStream output, O writer);

}
