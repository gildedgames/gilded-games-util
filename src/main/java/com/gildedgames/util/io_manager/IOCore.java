package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.exceptions.IOManagerTakenException;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.factory.ISerializeBehaviour;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOData;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.io_manager.io.IOSyncableDispatcher;
import com.gildedgames.util.io_manager.overhead.IOFileController;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOVolatileController;
import com.gildedgames.util.io_manager.util.IOManagerDefault;

public class IOCore implements IORegistry, IOFileController, IOVolatileController, IOManager
{

	private static final IOCore INSTANCE = new IOCore();

	private final List<IOManager> internalManagers = new ArrayList<>();

	private final SidedObject<List<IOSyncableDispatcher>> syncableDispatchers = new SidedObject<List<IOSyncableDispatcher>>(new ArrayList<IOSyncableDispatcher>(), new ArrayList<IOSyncableDispatcher>());

	protected final IOFileController fileComponent;

	protected final IOVolatileController volatileComponent;

	protected final IORegistry registryComponent;

	protected final IOManager defaultManager;

	private IOCore()
	{
		this.registryComponent = new IORegistryCore(this.internalManagers);
		this.fileComponent = new IOFileOverheadCore();
		this.volatileComponent = new IOVolatileOverheadCore();

		this.defaultManager = new IOManagerDefault("IOCore");
		this.internalManagers.add(this.defaultManager);
	}

	public static IOCore io()
	{
		return IOCore.INSTANCE;
	}

	public void dispatchDirtySyncables(SyncSide from)
	{
		for (IOSyncableDispatcher<?, ?> dispatcher : this.syncableDispatchers.instance())
		{
			if (dispatcher != null)
			{
				dispatcher.dispatchDirtySyncables(from);
			}
		}
	}

	public IOSyncableDispatcher getDispatcherFromID(String id)
	{
		for (IOSyncableDispatcher dispatcher : this.syncableDispatchers.instance())
		{
			if (dispatcher != null && dispatcher.getID().equals(id))
			{
				return dispatcher;
			}
		}

		return null;
	}

	public void registerDispatcher(IOSyncableDispatcher<?, ?> syncableDispatcher)
	{
		this.syncableDispatchers.client().add(syncableDispatcher);
		this.syncableDispatchers.server().add(syncableDispatcher);
	}

	/** Recommended to pass along an instance of the default implementation, IORegistryDefault.
	 * You MUST have a unique registryID, otherwise it will throw an IORegistryTakenException **/
	public void registerManager(IOManager manager)
	{
		for (IOManager external : this.internalManagers)
		{
			if (external.getID().equals(manager.getID()))
			{
				throw new IOManagerTakenException(manager);
			}
		}

		this.internalManagers.add(manager);
	}

	public IOManager getManager(String managerID)
	{
		for (IOManager external : this.internalManagers)
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
		for (IOManager external : this.internalManagers)
		{
			IORegistry registry = external.getRegistry();

			if (registry.isClassRegistered(clazz))
			{
				return external;
			}
		}

		UtilModule.print("Could not find IO manager for class " + clazz + ". Please register this class to a manager (it's also possible you've forgotten to register an IO manager).");

		return null;
	}

	@Override
	public IOManager getManager()
	{
		return this;
	}

	@Override
	public <T extends IO<I, O>, I, O> T get(String key, I input, IOFactory<I, O> ioFactory, IConstructor... objectConstructors)
	{
		return this.volatileComponent.get(key, input, ioFactory, objectConstructors);
	}

	@Override
	public <T extends IO<I, O>, I, O> void set(String key, O output, IOFactory<I, O> ioFactory, T objectToWrite)
	{
		this.volatileComponent.set(key, output, ioFactory, objectToWrite);
	}

	@Override
	public <T extends IO<I, O>, I, O> T clone(IOFactory<I, O> factory, T objectToClone) throws IOException
	{
		return this.volatileComponent.clone(factory, objectToClone);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> FILE readFile(File file, IOFactory<I, O> ioFactory, IConstructor... constructors) throws IOException
	{
		return this.fileComponent.readFile(file, ioFactory, constructors);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void writeFile(File file, FILE ioFile, IOFactory<I, O> ioFactory) throws IOException
	{
		this.fileComponent.writeFile(file, ioFile, ioFactory);
	}

	@Override
	public String getID()
	{
		return "";
	}

	/**
	 * Registers a class to serialize in the default manager.
	 * Registering through this method is highly discouraged,
	 * as there is no guarantee that other clients won't
	 * register under the same ID.
	 */
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
		return null;
	}

	@Override
	public IOVolatileController getVolatileController()
	{
		return this.volatileComponent;
	}

	@Override
	public <I, O> IOData<I, O> readFileMetadata(File file, IOFactory<I, O> ioFactory) throws IOException
	{
		return this.fileComponent.readFileMetadata(file, ioFactory);
	}

	@Override
	public <I, O, FILE extends IOFile<I, O>> void readFile(File file, FILE ioFile, IOFactory<I, O> ioFactory) throws IOException
	{
		this.fileComponent.readFile(file, ioFile, ioFactory);
	}

	@Override
	public IOFileController getFileController()
	{
		return this.fileComponent;
	}

	public IOManager getDefaultManager()
	{
		return this.defaultManager;
	}

}
