package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;

public class IOSerializerCore implements IOSerializer
{

	private List<IOManager> managers;

	protected IOSerializerCore(List<IOManager> managers)
	{
		this.managers = managers;
	}

	@Override
	public IORegistry getParentRegistry()
	{
		return IOCore.io();
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		IOManager manager = this.managers.get(0);
		IOSerializer serializer = manager.getSerializer();
		
		return serializer.readFile(file, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor constructor) throws IOException
	{
		IOManager manager = this.managers.get(0);
		IOSerializer serializer = manager.getSerializer();
		
		return serializer.readFile(file, ioFactory, constructor);
	}
	
	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		IOManager manager = this.managers.get(0);
		IOSerializer serializer = manager.getSerializer();
		
		serializer.readFile(file, ioFile, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		IOManager manager = this.managers.get(0);
		IOSerializer serializer = manager.getSerializer();
		
		serializer.writeFile(file, ioFile, ioFactory);
	}
	
}
