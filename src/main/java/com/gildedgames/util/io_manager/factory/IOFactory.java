package com.gildedgames.util.io_manager.factory;

import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;

public interface IOFactory<FILE extends IOFile<I, O>, I, O>
{
	
	boolean isKeyValueSystem();

	/**
	 * Creates a new Input object based on the given byte[]. 
	 */
	I createInput(byte[] reading);

	/**
	 * Creates an empty Output object to write data into.
	 */
	O createOutput();
	
	/**
	 * Creates a new raw Input object wrapping around an existing Input. 
	 */
	I createRawInput(I input);
	
	/**
	 * Creates an empty raw Output object wrapping around an existing Output.
	 */
	O createRawOutput(O output);
	
	/**
	 * Creates a new IOBridge object (which wraps around I) based on the given byte[].
	 */
	IOBridge createInputBridge(I input);
	
	/**
	 * Creates an empty IOBridge object (which wraps around O) to write data into.
	 */
	IOBridge createOutputBridge(O output);
	
	/**
	 * Creates a new InputRecorder which records String keys in the order they were set.
	 */
	InputRecorder<I> createInputRecorder(byte[] reading);
	
	/**
	 * Creates a new OutputArranger which rearranges data based on the given set of keys.
	 */
	OutputArranger<O> createOutputArranger();

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
