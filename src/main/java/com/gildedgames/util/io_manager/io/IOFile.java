package com.gildedgames.util.io_manager.io;

import java.io.IOException;

import com.gildedgames.util.io_manager.IOManager;

public interface IOFile<I, O>
{

	public void readFileData(IOManager manager, I reader) throws IOException;

	public void writeFileData(IOManager manager, O writer) throws IOException;

	public String getFileExtension();

	public String getDirectoryName();

}
