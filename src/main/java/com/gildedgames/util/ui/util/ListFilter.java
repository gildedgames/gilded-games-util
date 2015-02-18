package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

public class ListFilter
{
	
	public ListFilter()
	{
		
	}

	public <T> List<T> getTypesFrom(List list, Class<? extends T> typeClass)
	{
		List<T> returnList = new ArrayList<T>();
		
		for (Object obj : list)
		{
			if (obj != null && typeClass.isAssignableFrom(obj.getClass()))
			{
				returnList.add((T)obj);
			}
		}
		
		return returnList;
	}
	
}
