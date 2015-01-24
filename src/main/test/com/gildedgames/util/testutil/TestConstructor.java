package com.gildedgames.util.testutil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.gildedgames.util.io_manager.constructor.IConstructor;

public class TestConstructor implements IConstructor
{

	@Override
	public boolean isApplicable(Class<?> clazz)
	{
		return true;
	}

	@Override
	public <T> T construct(Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		final Constructor<T> constructor = clazz.getDeclaredConstructor(int.class);
		constructor.setAccessible(true);

		final T instance = constructor.newInstance(1);

		constructor.setAccessible(false);
		return instance;
	}

}
