package com.gildedgames.util.io_manager.overhead;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOData;

import java.io.IOException;

public interface IOSerializer
{

	IOManager getManager();

	/**
	 * Reads ALL data into the provided IOData instance, including sub data.
	 * @param io
	 * @param data
	 * @param factory
	 * @param constructors
	 * @return
	 * @throws IOException
	 */
	<I, O, DATA extends IOData<I, O>> DATA readData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory, IConstructor... constructors) throws IOException;

	/**
	 * Writes ALL data from the provided IOData instance, including sub data.
	 * @param io
	 * @param data
	 * @param factory
	 * @throws IOException
	 */
	<I, O, DATA extends IOData<I, O>> void writeData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> factory) throws IOException;

	/**
	 * Reads ONLY sub data into the provided IOData instance. This does not include its main data.
	 * @param io
	 * @param data
	 * @param ioFactory
	 * @return
	 * @throws IOException
	 */
	<I, O, DATA extends IOData<I, O>> DATA readSubData(ByteChunkPool chunkPool, IOFactory<I, O> ioFactory) throws IOException;

	/**
	 * Writes ONLY sub data from the provided IOData instance. This does not include its main data.
	 * @param io
	 * @param data
	 * @param ioFactory
	 * @throws IOException
	 */
	<I, O, DATA extends IOData<I, O>> void writeSubData(ByteChunkPool chunkPool, DATA data, IOFactory<I, O> ioFactory) throws IOException;

}
