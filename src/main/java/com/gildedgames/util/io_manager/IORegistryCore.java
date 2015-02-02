package com.gildedgames.util.io_manager;

import java.util.List;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IORegistryCore implements IORegistry
{
	
	private List<IORegistry> registries;

	protected IORegistryCore(List<IORegistry> registries)
	{
		this.registries = registries;
	}

	@Override
	public String getRegistryID()
	{
		return "";
	}

	@Override
	public void registerClass(Class<?> classToSerialize, int classID)
	{
		
	}

	@Override
	public void registerBehavior(Class<?> classToSerialize, ISerializeBehaviour<?> serializeBehaviour)
	{
		
	}

	@Override
	public <T> T create(Class<T> registeredClass, IConstructor classConstructor)
	{
		IORegistry registry = IOCore.io().getRegistry(registeredClass);
		
		if (registry != null)
		{
			return registry.create(registeredClass, classConstructor);
		}
		
		return null;
	}

	@Override
	public Object create(String registryID, int registeredClassID)
	{
		IORegistry registry = IOCore.io().getRegistry(registryID);
		
		if (registry != null)
		{
			return registry.create(registryID, registeredClassID);
		}
		
		return null;
	}

	@Override
	public Object create(String registryID, int registeredClassID, IConstructor classConstructor)
	{
		IORegistry registry = IOCore.io().getRegistry(registryID);
		
		if (registry != null)
		{
			return registry.create(registryID, registeredClassID, classConstructor);
		}
		
		return null;
	}

	@Override
	public Class<?> getClass(String registryID, int registeredClassID)
	{
		IORegistry registry = IOCore.io().getRegistry(registryID);
		
		if (registry != null)
		{
			return registry.getClass(registryID, registeredClassID);
		}
		
		return null;
	}

	@Override
	public int getID(Class<?> registeredClass)
	{
		IORegistry registry = IOCore.io().getRegistry(registeredClass);
		
		if (registry != null)
		{
			return registry.getID(registeredClass);
		}
		
		return -1;
	}

	@Override
	public int getID(Object objectOfRegisteredClass)
	{
		return this.getID(objectOfRegisteredClass.getClass());
	}

	@Override
	public boolean isClassRegistered(Class<?> clazz)
	{
		for (IORegistry registry : this.registries)
		{
			if (registry.isClassRegistered(clazz))
			{
				return true;
			}
		}
		
		return false;
	}

}
