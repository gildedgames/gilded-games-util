package com.gildedgames.util.io_manager.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IORegistryDefault implements IORegistry
{
	
	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	private final Map<Integer, Class<?>> IDToClassMapping = new HashMap<Integer, Class<?>>();

	private final Map<Class<?>, Integer> classToIDMapping = new HashMap<Class<?>, Integer>();

	private final Map<Class<?>, ISerializeBehaviour<?>> serializeBehaviors = new HashMap<Class<?>, ISerializeBehaviour<?>>();
	
	private final String registryID;
	
	private final IOSerializer serializer;
	
	private final IOSerializerVolatile serializerVolatile;
	
	public IORegistryDefault(String registryID, IOSerializer serializer, IOSerializerVolatile serializerVolatile)
	{
		this.registryID = registryID;
		
		this.serializer = serializer;
		this.serializerVolatile = serializerVolatile;
	}
	
	public IORegistryDefault(String registryID)
	{
		this.registryID = registryID;
		
		this.serializer = new IOSerializerDefault(this);
		this.serializerVolatile = new IOSerializerVolatileDefault(this);
	}
	
	@Override
	public String getRegistryID()
	{
		return this.registryID;
	}
	
	@Override
	public IOSerializer getSerializer()
	{
		return this.serializer;
	}
	
	@Override
	public IOSerializerVolatile getVolatileSerializer()
	{
		return this.serializerVolatile;
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
	public <T> T create(Class<T> registeredClass, IConstructor classConstructor)
	{
		if (registeredClass == null)
		{
			return null;
		}

		T instance = null;

		try
		{
			instance = classConstructor.construct(registeredClass);

			for (final Class factoryClazz : this.serializeBehaviors.keySet())
			{
				if (factoryClazz.isInstance(instance))
				{
					final ISerializeBehaviour behaviour = this.serializeBehaviors.get(factoryClazz);
					behaviour.postCreate(instance);
				}
			}

			return instance;
		}
		catch (final InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (final IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (final IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (final InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e)
		{
			UtilCore.debugPrint(registeredClass.getCanonicalName() + ".class is missing the empty constructor! Contact code monkeys immediately!");
			e.printStackTrace();
		}
		catch (final SecurityException e)
		{
			e.printStackTrace();
		}

		return instance;
	}

	@Override
	public Object create(String registryID, int registeredClassID)
	{
		return this.create(registryID, registeredClassID, IORegistryDefault.defaultConstructor);
	}

	@Override
	public Object create(String registryID, int registeredClassID, IConstructor classConstructor)
	{
		final Class<?> clazz = this.getClass(registryID, registeredClassID);

		return this.create(clazz, classConstructor);
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
