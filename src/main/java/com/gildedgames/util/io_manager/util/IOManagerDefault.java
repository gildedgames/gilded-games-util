package com.gildedgames.util.io_manager.util;

import com.gildedgames.util.io_manager.overhead.*;

public class IOManagerDefault implements IOManager
{

	protected final IORegistry registryComponent;

	protected final IOSerializer serializerComponent;

	protected final IOVolatileController volatileComponent;

	protected final String name;

	protected final IOFileController serializer = new IOFileControllerDefault(this);

	public IOManagerDefault(IORegistry registry, IOSerializer serializer, IOVolatileController volatileSerializer, String id)
	{
		this.registryComponent = registry;
		this.serializerComponent = serializer;
		this.volatileComponent = volatileSerializer;
		this.name = id;
	}

	public IOManagerDefault(String id)
	{
		this.registryComponent = new IORegistryDefault();
		this.serializerComponent = new IOSerializerDefault(this);
		this.volatileComponent = new IOVolatileControllerDefault(this);
		this.name = id;
	}

	@Override
	public IORegistry getRegistry()
	{
		return this.registryComponent;
	}

	@Override
	public IOSerializer getSerializer()
	{
		return this.serializerComponent;
	}

	@Override
	public IOVolatileController getVolatileController()
	{
		return this.volatileComponent;
	}

	@Override
	public String getID()
	{
		return this.name;
	}

	@Override
	public IOFileController getFileController()
	{
		return this.serializer;
	}

}
