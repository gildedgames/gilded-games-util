package com.gildedgames.util.io_manager.util;

import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializerExternal;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOManagerDefault implements IOManager
{

	protected IORegistry registryComponent;

	protected IOSerializerExternal serializerComponent;

	protected IOSerializerVolatile volatileComponent;

	protected String name;

	public IOManagerDefault(IORegistry registry, IOSerializerExternal serializer, IOSerializerVolatile volatileSerializer, String id)
	{
		this.registryComponent = registry;
		this.serializerComponent = serializer;
		this.volatileComponent = volatileSerializer;
		this.name = id;
	}

	public IOManagerDefault(String id)
	{
		this.registryComponent = new IORegistryDefault();
		this.serializerComponent = new IOSerializerExternalDefault(this);
		this.volatileComponent = new IOSerializerVolatileDefault(this);
		this.name = id;
	}

	@Override
	public IORegistry getRegistry()
	{
		return this.registryComponent;
	}

	@Override
	public IOSerializerExternal getSerializer()
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

}
