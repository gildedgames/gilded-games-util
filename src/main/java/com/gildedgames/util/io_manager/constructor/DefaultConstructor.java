package com.gildedgames.util.io_manager.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DefaultConstructor implements IConstructor
{

	@Override
	public boolean isApplicable(Class<?> clazz)
	{
		return true;
	}

	@Override
	public <T> T construct(Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		final Constructor<T> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);

		final T instance = constructor.newInstance();

		constructor.setAccessible(false);

		return instance;
	}

}
