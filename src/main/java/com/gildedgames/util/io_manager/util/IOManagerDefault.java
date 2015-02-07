package com.gildedgames.util.io_manager.util;

import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerInternal;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOManagerDefault implements IOManager
{

	protected final IORegistry registryComponent;

	protected final IOSerializerInternal serializerComponent;

	protected final IOSerializerVolatile volatileComponent;

	protected final String name;

	protected final IOSerializer serializer = new IOSerializerDefault(this);

	public IOManagerDefault(IORegistry registry, IOSerializerInternal serializer, IOSerializerVolatile volatileSerializer, String id)
	{
		this.registryComponent = registry;
		this.serializerComponent = serializer;
		this.volatileComponent = volatileSerializer;
		this.name = id;
	}

	public IOManagerDefault(String id)
	{
		this.registryComponent = new IORegistryDefault();
		this.serializerComponent = new IOSerializerInternalDefault(this);
		this.volatileComponent = new IOSerializerVolatileDefault(this);
		this.name = id;
	}

	@Override
	public IORegistry getRegistry()
	{
		return this.registryComponent;
	}

	@Override
	public IOSerializerInternal getInternalSerializer()
	{
		return this.serializerComponent;
	}

	@Override
	public IOSerializerVolatile getVolatileSerializer()
	{
		return this.volatileComponent;
	}

	@Override
	public String getID()
	{
		return this.name;
	}

	@Override
	public IOSerializer getSerializer()
	{
		return this.serializer;
	}

}
