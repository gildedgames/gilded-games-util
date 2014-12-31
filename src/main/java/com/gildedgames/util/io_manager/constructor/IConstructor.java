package com.gildedgames.util.io_manager.constructor;

import java.lang.reflect.InvocationTargetException;

/**
 * Used in IOManager to construct the given Classes in a different way than the empty constructor.
 * Can be used to pass along any must-have objects.
 * @author Emile
 *
 */
public interface IConstructor
{

	boolean isApplicable(Class clazz);

	Object construct(Class clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

}
