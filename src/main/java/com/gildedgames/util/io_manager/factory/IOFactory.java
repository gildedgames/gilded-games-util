package com.gildedgames.util.io_manager.factory;

import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;

public interface IOFactory<FILE extends IOFile<I, O>, I, O>
{

	I getInput(byte[] reading);

	O getOutput();

	Class<?> readSerializedClass(I input);

	void writeSerializedClass(O output, Class<?> classToWrite);

	Class<?> getSerializedClass(String key, I input);

	void setSerializedClass(String key, O output, Class<?> classToWrite);

	File getFileFromName(FILE data, String name);

	void preReading(FILE data, File from, I reader);

	void preReadingMetadata(IOFileMetadata<I, O> metadata, File from, I reader);

	byte[] finishWriting(O writer);

	I convertToInput(O input);

}
