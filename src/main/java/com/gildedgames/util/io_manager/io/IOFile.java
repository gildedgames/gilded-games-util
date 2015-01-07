package com.gildedgames.util.io_manager.io;

import java.io.IOException;

import com.gildedgames.util.io_manager.IOManager;

public interface IOFile<I, O>
{

	void readFromFile(IOManager manager, I reader) throws IOException;

	void writeToFile(IOManager manager, O writer) throws IOException;
	
	Class getDataClass();

	String getFileExtension();

	String getDirectoryName();

}
