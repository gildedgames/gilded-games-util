package com.gildedgames.util.io_manager.io;

import java.io.File;

public interface IOFileMetadata<I, O> extends IOFile<I, O>
{
	/**
	 * Sets the File where this Metadata object is read from.
	 * @param file
	 */
	void setFileLocation(File file);
}
