package com.gildedgames.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class ObjectFilter
{

	private ObjectFilter()
	{

	}

	public static abstract class FilterCondition
	{

		private List<Object> data;

		public FilterCondition(List<Object> data)
		{
			this.data = data;
		}

		public List<Object> data()
		{
			return this.data;
		}

		public abstract boolean isType(Object object);

	}

	public static <T> List<T> getTypesFrom(Object[] array, Class<? extends T> typeClass)
	{
		return getTypesFrom(Arrays.asList(array), typeClass);
	}

	public static <T> List<T> getTypesFrom(Object[] array, FilterCondition condition)
	{
		return getTypesFrom(Arrays.asList(array), condition);
	}

	@SuppressWarnings("unchecked")
	public static <T, K> Map<K, T> getTypesFromKeys(Map<?, ?> map, Class<? extends K> keyClass, Class<? extends T> typeClass)
	{
		Map<K, T> returnMap = new HashMap<K, T>();

		for (Map.Entry<?, ?> entry : map.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (key != null && typeClass.isAssignableFrom(key.getClass()))
			{
				returnMap.put((K) key, (T) value);
			}
		}

		return returnMap;
	}

	@SuppressWarnings("unchecked")
	public static <T, K> Map<K, T> getTypesFromValues(Map<?, ?> map, Class<? extends K> keyClass, Class<? extends T> typeClass)
	{
		Map<K, T> returnMap = new HashMap<K, T>();

		for (Map.Entry<?, ?> entry : map.entrySet())
		{
			Object key = entry.getKey();
			Object value = entry.getValue();

			if (value != null && typeClass.isAssignableFrom(value.getClass()))
			{
				returnMap.put((K) key, (T) value);
			}
		}

		return returnMap;
	}

	public static <T> List<T> getTypesFrom(Iterable<?> iterable, Class<? extends T> typeClass)
	{
		return ObjectFilter.getTypesFrom(Lists.newArrayList(iterable), typeClass);
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
	public static <T> List<T> getTypesFrom(Collection<?> list, FilterCondition condition)
	{
		List<T> returnList = new ArrayList<T>();

		for (Object obj : list)
		{
			if (obj != null && condition.isType(obj))
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
