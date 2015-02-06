package com.gildedgames.util.io_manager.factory;

import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;

public interface IOFactory<FILE extends IOFile<I, O>, I, O>
{

	/**
	 * Create a new Input object based on the given byte[]. 
	 */
	I getInput(byte[] reading);

	/**
	 * Create an empty Output object to read data in
	 */
	O getOutput();

	/**
	 * Returns the underlying byte[] of the Output,
	 * or converts the data structure in the Output to a byte[].
	 */
	byte[] getBytesFrom(O output);

	/**
	 * Reads back a Class object from the Input 
	 */
	Class<?> readSerializedClass(I input);

	/**
	 * Writes a Class object to an Output
	 */
	void writeSerializedClass(O output, Class<?> classToWrite);

	Class<?> getSerializedClass(String key, I input);

	void setSerializedClass(String key, O output, Class<?> classToWrite);

	File getFileFromName(FILE data, String name);

	void preReading(FILE data, File from, I input);

	void preReadingMetadata(IOFileMetadata<I, O> metadata, File from, I input);

}
