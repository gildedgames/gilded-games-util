package com.gildedgames.util.io_manager.io;

import com.google.common.base.Optional;

/**
 * Represents an {@link IO IO} object that has layered data.
 * This can be used if you only need to know about parts of
 * this class's data at some points, for example by using
 * metadata descriptors of your data.
 * 
 * The IOManager guarantees that this data is written at the
 * front of files, to make sure there is minimal performance loss.
 * @author Emile
 *
 * @see IO
 */
public interface IOData<I, O> extends IO<I, O>
{

	/**
	 * Returns the data describing this file. 
	 */
	Optional<IOData<I, O>> getSubData();

	/**
	 * Passes the subdata that was read back to this class to
	 * be processed. 
	 */
	void setSubData(IOData<I, O> data);
}
