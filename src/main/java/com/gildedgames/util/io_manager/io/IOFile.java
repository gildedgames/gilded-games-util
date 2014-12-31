package com.gildedgames.util.io_manager.io;

import java.io.IOException;

import com.gildedgames.util.io_manager.IOManager;

public interface IOFile<I, O>
{

	void readFileData(IOManager manager, I reader) throws IOException;

	void writeFileData(IOManager manager, O writer) throws IOException;

	String getFileExtension();

	String getDirectoryName();

}
