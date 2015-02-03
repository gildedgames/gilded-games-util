package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.exceptions.IOManagerTakenException;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOCore implements IORegistry, IOSerializer, IOSerializerVolatile, IOManager
{

	private static final IOCore INSTANCE = new IOCore();

	private List<IOManager> externalManagers = new ArrayList<IOManager>();

	protected IOSerializer fileComponent;

	protected IOSerializerVolatile volatileComponent;

	protected IORegistry registryComponent;

	private IOCore()
	{
		this.registryComponent = new IORegistryCore(this.externalManagers);
		this.fileComponent = new IOSerializerCore();
		this.volatileComponent = new IOSerializerVolatileCore();
	}

	public static IOCore io()
	{
		return IOCore.INSTANCE;
	}

	/** Recommended to pass along an instance of the default implementation, IORegistryDefault.
	 * You MUST have a unique registryID, otherwise it will throw an IORegistryTakenException **/
	public void registerManager(IOManager manager)
	{
		for (IOManager external : this.externalManagers)
		{
			if (external.getID().equals(manager.getID()))
			{
				throw new IOManagerTakenException(manager);
			}
		}

		this.externalManagers.add(manager);
	}

	public IOManager getManager(String managerID)
	{
		for (IOManager external : this.externalManagers)
		{
			if (external.getID().equals(managerID))
			{
				return external;
			}
		}

		return null;
	}

	public IOManager getManager(Class<?> clazz)
	{
		for (IOManager external : this.externalManagers)
		{
			IORegistry registry = external.getRegistry();

			if (registry.isClassRegistered(clazz))
			{
				return external;
			}
		}

		return null;
	}

	@Override
	public IOManager getManager()
	{
		return this;
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory)
	{
		return this.volatileComponent.read(input, ioFactory);
	}

	@Override
	public <T extends IO<I, ?>, I> T read(I input, IOFactory<?, I, ?> ioFactory, IConstructor... objectConstructors)
	{
		return this.volatileComponent.read(input, ioFactory, objectConstructors);
	}

	@Override
	public <T extends IO<?, O>, O> void write(O output, IOFactory<?, ?, O> ioFactory, T objectToWrite)
	{
		this.volatileComponent.write(output, ioFactory, objectToWrite);
	}

	@Override
	public <T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory)
	{
		return this.volatileComponent.get(key, input, ioFactory);
	}

	@Override
	public <T extends IO<I, ?>, I> T get(String key, I input, IOFactory<?, I, ?> ioFactory, IConstructor... objectConstructors)
	{
		return this.volatileComponent.get(key, input, ioFactory, objectConstructors);
	}

	@Override
	public <T extends IO<?, O>, O> void set(String key, O output, IOFactory<?, ?, O> ioFactory, T objectToWrite)
	{
		this.volatileComponent.set(key, output, ioFactory, objectToWrite);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(IOFactory<?, I, O> factory, T objectToClone) throws IOException
	{
		return this.volatileComponent.clone(factory, objectToClone);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		return this.fileComponent.readFile(file, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<FILE, I, O> ioFactory, IConstructor... constructors) throws IOException
	{
		return this.fileComponent.readFile(file, ioFactory, constructors);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		this.fileComponent.writeFile(file, ioFile, ioFactory);
	}

	@Override
	public String getID()
	{
		return "";
	}

	@Override
	public void registerClass(Class<?> classToSerialize, int classID)
	{
		this.registryComponent.registerClass(classToSerialize, classID);
	}

	@Override
	public void registerBehavior(Class<?> classToSerialize, ISerializeBehaviour<?> serializeBehaviour)
	{
		this.registryComponent.registerBehavior(classToSerialize, serializeBehaviour);
	}

	@Override
	public <T> T create(Class<T> registeredClass, IConstructor... classConstructors)
	{
		return this.registryComponent.create(registeredClass, classConstructors);
	}

	@Override
	public Object create(String registryID, int registeredClassID)
	{
		return this.registryComponent.create(registryID, registeredClassID);
	}

	@Override
	public Object create(String registryID, int registeredClassID, IConstructor... classConstructors)
	{
		return this.registryComponent.create(registryID, registeredClassID, classConstructors);
	}

	@Override
	public Class<?> getClass(String registryID, int registeredClassID)
	{
		return this.registryComponent.getClass(registryID, registeredClassID);
	}

	@Override
	public int getID(Class<?> registeredClass)
	{
		return this.registryComponent.getID(registeredClass);
	}

	@Override
	public int getID(Object objectOfRegisteredClass)
	{
		return this.registryComponent.getID(objectOfRegisteredClass);
	}

	@Override
	public boolean isClassRegistered(Class<?> clazz)
	{
		return this.registryComponent.isClassRegistered(clazz);
	}

	@Override
	public IORegistry getRegistry()
	{
		return this.registryComponent;
	}

	@Override
	public IOSerializer getSerializer()
	{
		return this.fileComponent;
	}

	@Override
	public IOSerializerVolatile getVolatileSerializer()
	{
		return this.volatileComponent;
	}

	@Override
	public <I, O> IOFileMetadata<I, O> readFileMetadata(File file, IOFactory<?, I, O> ioFactory) throws IOException
	{
		return this.fileComponent.readFileMetadata(file, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<FILE, I, O> ioFactory) throws IOException
	{
		this.fileComponent.readFile(file, ioFile, ioFactory);
	}

}
