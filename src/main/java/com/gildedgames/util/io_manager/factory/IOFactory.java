package com.gildedgames.util.io_manager.factory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IORegistry;

public interface IOFactory<FILE extends IOFile<I, O>, I, O>
{

	I getInput(DataInputStream input, IORegistry registry);

	O getOutput(DataOutputStream output, IORegistry registry);
	
	Class<?> readClass(I input, IORegistry registry);
	
	void writeClass(O output, Class<?> classToWrite, IORegistry registry);

	File getFileFromName(FILE data, String name);

	void preReading(FILE data, File from, I reader);

	void preReadingMetadata(IOFileMetadata<I, O> metadata, File from, I reader);

	void finishWriting(DataOutputStream output, O writer);

}
