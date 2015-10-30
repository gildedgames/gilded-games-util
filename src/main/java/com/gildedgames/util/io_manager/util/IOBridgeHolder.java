package com.gildedgames.util.io_manager.util;

import java.util.Collections;
import java.util.List;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.IOObserver;

public class IOBridgeHolder implements IOFactory<IOBridge, IOBridge>
{
	
	private IOBridge input, output;
	
	private IOBridgeHolder()
	{
		
	}
	
	public static IOBridgeHolder in(IOBridge input)
	{
		IOBridgeHolder factory = new IOBridgeHolder();
		
		factory.input = input;
	
		return factory;
	}
	
	public static IOBridgeHolder out(IOBridge output)
	{
		IOBridgeHolder factory = new IOBridgeHolder();
		
		factory.output = output;
	
		return factory;
	}

	@Override
	public IOBridge createInput(byte[] reading)
	{
		return this.input;
	}

	@Override
	public IOBridge createOutput()
	{
		return this.output;
	}

	@Override
	public IOBridge createInputBridge(IOBridge input)
	{
		return this.input;
	}

	@Override
	public IOBridge createOutputBridge(IOBridge output)
	{
		return this.output;
	}

	@Override
	public List<IOObserver<IOBridge, IOBridge>> getObservers()
	{
		return Collections.emptyList();
	}

}
