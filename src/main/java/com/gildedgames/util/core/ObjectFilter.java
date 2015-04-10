package com.gildedgames.util.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scala.actors.threadpool.Arrays;

public class ObjectFilter
{

	private ObjectFilter()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getTypesFrom(Object[] array, Class<? extends T> typeClass)
	{
		return getTypesFrom(Arrays.asList(array), typeClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getTypesFrom(Collection<?> list, Class<? extends T> typeClass)
	{
		List<T> returnList = new ArrayList<T>();

		for (Object obj : list)
		{
			if (obj != null && typeClass.isAssignableFrom(obj.getClass()))
			{
				returnList.add((T) obj);
			}
		}

		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getType(Object object, Class<? extends T> typeClass)
	{
		T returnObject = null;

		if (object != null && typeClass.isAssignableFrom(object.getClass()))
		{
			returnObject = (T) object;
		}

		return returnObject;
	}

}
