package com.gildedgames.util.io_manager.io;

import java.io.File;

import com.google.common.base.Optional;

public interface IOFileMetadata<I, O> extends IO<I, O>
{
	/**
	 * Sets the File where this Metadata object is read from.
	 * @param file
	 */
	void setFileLocation(File file);

	/**
	 * Returns the data describing this file. Can be used to optimize reading essential data.
	 * Make sure Metadata have default constructors!
	 */
	Optional<IOFileMetadata<I, O>> getMetadata();

	void setMetadata(IOFileMetadata<I, O> metadata);
}
