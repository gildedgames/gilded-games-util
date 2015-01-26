package com.gildedgames.util.io_manager.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

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

		if (constructor != null)
		{
			final T instance = constructor.newInstance();

			constructor.setAccessible(false);
			
			if (instance != null)
			{
				return instance;
			}
		}

		final Constructor<T> constr = (Constructor<T>) clazz.getDeclaredConstructors()[0];
	    final List<Object> params = new ArrayList<Object>();
	    
	    constr.setAccessible(false);
	    
	    for (Class<?> pType : constr.getParameterTypes())
	    {
	        params.add((pType.isPrimitive()) ? ClassUtils.primitiveToWrapper(pType).newInstance() : null);
	    }
	    
	    final T instance = constr.newInstance(params.toArray());

		return instance;
	}

}
