package com.gildedgames.util.io_manager.overhead;

import java.io.File;
import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOData;

public interface IOFileController
{

	IOManager getManager();

	<I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<I, O> factory, IConstructor... constructors) throws IOException;

	<I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<I, O> factory) throws IOException;

	<I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<I, O> factory) throws IOException;

	<I, O> IOData<I, O> readFileMetadata(File file, IOFactory<I, O> ioFactory) throws IOException;

}