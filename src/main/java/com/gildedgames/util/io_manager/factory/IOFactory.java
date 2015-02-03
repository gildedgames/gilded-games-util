package com.gildedgames.util.io_manager.factory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IORegistry;

public interface IOFactory<FILE extends IOFile<I, O>, I, O>
{

	I getInput(DataInputStream input);

	O getOutput(DataOutputStream output);
	
	Class<?> readSerializedClass(I input);
	
	void writeSerializedClass(O output, Class<?> classToWrite);
	
	Class<?> getSerializedClass(String key, I input);
	
	void setSerializedClass(String key, O output, Class<?> classToWrite);

	File getFileFromName(FILE data, String name);

	void preReading(FILE data, File from, I reader);

	void preReadingMetadata(IOFileMetadata<I, O> metadata, File from, I reader);

	void finishWriting(DataOutputStream output, O writer);

}
