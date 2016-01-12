package com.gildedgames.util.io_manager.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.exceptions.ClassMissingInitException;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.overhead.IORegistry;

public class IORegistryDefault implements IORegistry
{

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private final Map<Integer, Class<?>> IDToClassMapping = new HashMap<>();

	private final Map<Class<?>, Integer> classToIDMapping = new HashMap<>();

	private final Map<Class<?>, ISerializeBehaviour<?>> serializeBehaviors = new HashMap<>();

	public IORegistryDefault()
	{
	}

	@Override
	public void registerClass(Class<?> classToSerialize, int classID)
	{
		this.IDToClassMapping.put(classID, classToSerialize);
		this.classToIDMapping.put(classToSerialize, classID);
	}

	@Override
	public void registerBehavior(Class<?> classToSerialize, ISerializeBehaviour<?> serializeBehaviour)
	{
		this.serializeBehaviors.put(classToSerialize, serializeBehaviour);
	}

	@Override
	public <T> T create(Class<T> registeredClass, IConstructor... classConstructors)
	{
		if (registeredClass == null)
		{
			return null;
		}

		T instance = null;
		IConstructor constructor = defaultConstructor;

		for (IConstructor specialConstructor : classConstructors)
		{
			if (specialConstructor.isApplicable(registeredClass))
			{
				constructor = specialConstructor;
				break;
			}
		}

		try
		{

			instance = constructor.construct(registeredClass);

			for (final Class<?> factoryClazz : this.serializeBehaviors.keySet())
			{
				if (factoryClazz.isInstance(instance))
				{
					@SuppressWarnings("rawtypes")
					final ISerializeBehaviour behaviour = this.serializeBehaviors.get(factoryClazz);
					behaviour.postCreate(instance);
				}
			}

			return instance;
		}
		catch (final InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException | SecurityException e)
		{
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e)
		{
			throw new ClassMissingInitException(registeredClass);
		}

		return instance;
	}

	@Override
	public Object create(String registryID, int registeredClassID)
	{
		return this.create(registryID, registeredClassID, IORegistryDefault.defaultConstructor);
	}

	@Override
	public Object create(String registryID, int registeredClassID, IConstructor... classConstructors)
	{
		final Class<?> clazz = this.getClass(registryID, registeredClassID);

		return this.create(clazz, classConstructors);
	}

	@Override
	public Class<?> getClass(String registryID, int registeredClassID)
	{
		return this.IDToClassMapping.get(registeredClassID);
	}

	@Override
	public int getID(Class<?> registeredClass)
	{
		if (!this.classToIDMapping.containsKey(registeredClass))
		{
			throw new IllegalArgumentException("Object's class isn't registered! Class: " + registeredClass.getCanonicalName());
		}

		return this.classToIDMapping.get(registeredClass);
	}

	@Override
	public int getID(Object objectOfRegisteredClass)
	{
		return this.getID(objectOfRegisteredClass.getClass());
	}

	@Override
	public boolean isClassRegistered(Class<?> clazz)
	{
		return this.classToIDMapping.containsKey(clazz);
	}

}
