package com.gildedgames.util.io_manager.constructor;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

		if (instance != null)
		{
			return instance;
		}

		@SuppressWarnings("unchecked")
		final Constructor<T> constr = (Constructor<T>) clazz.getDeclaredConstructors()[0];
		final List<Object> params = new ArrayList<Object>();

		constr.setAccessible(false);

		for (Class<?> pType : constr.getParameterTypes())
		{
			params.add(pType.isPrimitive() ? ClassUtils.primitiveToWrapper(pType).newInstance() : null);
		}

		return constr.newInstance(params.toArray());
	}

}
