package com.gildedgames.util.io_manager.overhead;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;

public interface IORegistry
{
	
	String getRegistryID();
	
	IOSerializer getSerializer();
	
	IOSerializerVolatile getVolatileSerializer();

	void registerClass(Class<?> classToSerialize, int classID);
	 
	void registerBehavior(Class<?> classToSerialize, ISerializeBehaviour<?> serializeBehaviour);
	
	<T> T create(Class<T> registeredClass, IConstructor classConstructor);
	
	Object create(String registryID, int registeredClassID);
	
	Object create(String registryID, int registeredClassID, IConstructor classConstructor);
	
	Class<?> getClass(String registryID, int registeredClassID);
	 
	int getID(Class<?> registeredClass);
	
	int getID(Object objectOfRegisteredClass);
	
	boolean isClassRegistered(Class<?> clazz);
	
}
