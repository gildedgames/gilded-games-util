package com.gildedgames.util.io_manager.io;

import java.io.IOException;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.IReader;
import com.gildedgames.util.io_manager.IWriter;

public interface IOFile<I extends IReader, O extends IWriter>
{

	public void readFileData(IOManager manager, I reader) throws IOException;

	public void writeFileData(IOManager manager, O writer) throws IOException;

	public String getFileExtension();

	public String getDirectoryName();

}
