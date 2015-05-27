package com.gildedgames.util.io_manager.exceptions;

public class ClassMissingInitException extends RuntimeException
{

	public ClassMissingInitException(Class<?> clazz)
	{
		super(clazz.getCanonicalName() + ".class is missing the empty constructor! Contact code monkeys immediately!");
	}

	private static final long serialVersionUID = 1292643287682344178L;

}
