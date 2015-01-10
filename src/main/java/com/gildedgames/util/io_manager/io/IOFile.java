package com.gildedgames.util.io_manager.io;

import java.io.IOException;

import com.gildedgames.util.io_manager.IOManager;
import com.google.common.base.Optional;

public interface IOFile<I, O>
{

	void readFromFile(IOManager manager, I reader) throws IOException;

	void writeToFile(IOManager manager, O writer) throws IOException;

	Class getDataClass();

	String getFileExtension();

	String getDirectoryName();

	/**
	 * Returns the data describing this file. Can be used to optimize reading essential data.
	 * Make sure Metadata have default constructors!
	 */
	Optional<IOFileMetadata<I, O>> getMetadata();

	void setMetadata(IOFileMetadata<I, O> metadata);

}
