package com.gildedgames.util.io_manager.factory;

import java.util.List;

public interface IOFactory<I, O>
{

	/**
	 * Creates a new Input object based on the given byte[]. 
	 * @param orderDependent TODO
	 */
	I createInput(boolean orderDependent, byte[] reading);

	/**
	 * Creates an empty Output object to write data into.
	 * @param orderDependent TODO
	 */
	O createOutput(boolean orderDependent);
	
	/**
	 * Creates a new IOBridge object (which wraps around I) based on the given byte[].
	 */
	IOBridge createInputBridge(I input);
	
	/**
	 * Creates an empty IOBridge object (which wraps around O) to write data into.
	 */
	IOBridge createOutputBridge(O output);

	/**
	 * Return a list of optional IOObservers which can inject
	 * additional functionality into the IO process.
	 * @return A list of IOObservers
	 */
	List<IOObserver<I, O>> getObservers();
	
}
