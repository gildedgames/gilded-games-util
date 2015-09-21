package com.gildedgames.util.io_manager.exceptions;

public class ClassNotRegisteredException extends RuntimeException
{

	public ClassNotRegisteredException(Class<?> clazz)
	{
		super(clazz.getName() + " not registered in any IO manager.");
	}

	private static final long serialVersionUID = 7786301173171412041L;

}
