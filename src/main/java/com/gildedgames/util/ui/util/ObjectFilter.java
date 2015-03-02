package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

public class ObjectFilter
{

	public ObjectFilter()
	{

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getTypesFrom(List<?> list, Class<? extends T> typeClass)
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
	public <T> T getType(Object object, Class<? extends T> typeClass)
	{
		T returnObject = null;

		if (object != null && typeClass.isAssignableFrom(object.getClass()))
		{
			returnObject = (T) object;
		}

		return returnObject;
	}

}
