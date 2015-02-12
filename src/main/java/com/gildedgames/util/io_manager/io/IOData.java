package com.gildedgames.util.io_manager.io;

import com.google.common.base.Optional;

public interface IOData<I, O> extends IO<I, O>
{

	/**
	 * Returns the data describing this file. Can be used to optimize reading essential data.
	 * Make sure Metadata have default constructors!
	 */
	Optional<IOData<I, O>> getSubData();

	void setSubData(IOData<I, O> data);
}
