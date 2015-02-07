package com.gildedgames.util.io_manager.overhead;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;

public interface IOSerializerInternal
{

	IOManager getManager();

	<I, O, FILE extends IOFile<I, O>> FILE readFile(DataInputStream input, FILE ioFile, File file, IOFactory<FILE, I, O> ioFactory) throws IOException;

	<I, O, FILE extends IOFile<I, O>> FILE readFile(DataInputStream input, FILE ioFile, File file, IOFactory<FILE, I, O> ioFactory, IConstructor... constructors) throws IOException;

	<I, O, FILE extends IOFile<I, O>> void writeFile(DataOutputStream output, File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException;

	<I, O> IOFileMetadata<I, O> readFileMetadata(DataInputStream input, File file, IOFactory<?, I, O> ioFactory) throws IOException;

}
