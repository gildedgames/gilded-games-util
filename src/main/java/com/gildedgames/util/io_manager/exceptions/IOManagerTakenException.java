package com.gildedgames.util.io_manager.exceptions;

import com.gildedgames.util.io_manager.overhead.IOManager;

public class IOManagerTakenException extends RuntimeException
{

	private static final long serialVersionUID = 2107293727864570772L;

	public IOManagerTakenException(IOManager manager)
	{
		super("The returned getRegistryID() value ( '" + manager.getID() + "' ) from Class '" + manager.getClass().getCanonicalName() + "' has already been taken by another IORegistry!");
	}

}
