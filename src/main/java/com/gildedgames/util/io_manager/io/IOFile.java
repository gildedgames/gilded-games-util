package com.gildedgames.util.io_manager.io;

import com.google.common.base.Optional;

public interface IOFile<I, O> extends IO<I, O>
{

	Class<?> getDataClass();

	String getFileExtension();

	String getDirectoryName();

	/**
	 * Returns the data describing this file. Can be used to optimize reading essential data.
	 * Make sure Metadata have default constructors!
	 */
	Optional<IOFileMetadata<I, O>> getMetadata();

	void setMetadata(IOFileMetadata<I, O> metadata);

}
