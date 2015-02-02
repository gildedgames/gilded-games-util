package com.gildedgames.util.io_manager.exceptions;

import com.gildedgames.util.io_manager.overhead.IORegistry;

public class IORegistryTakenException extends Exception
{

	public IORegistryTakenException(IORegistry registry)
	{
		super("The returned getRegistryID() value ( '" + registry.getRegistryID() + "' ) from Class '" + registry.getClass().getCanonicalName() + "' has already been taken by another IORegistry!");
	}
	
}
