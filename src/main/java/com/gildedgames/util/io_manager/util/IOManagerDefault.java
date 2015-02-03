package com.gildedgames.util.io_manager.util;

import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOSerializer;
import com.gildedgames.util.io_manager.overhead.IOSerializerVolatile;

public class IOManagerDefault implements IOManager
{
	
	protected IORegistry registryComponent;
	
	protected IOSerializer serializerComponent;
	
	protected IOSerializerVolatile volatileComponent;
	
	public IOManagerDefault(IORegistry registry, IOSerializer serializer, IOSerializerVolatile volatileSerializer)
	{
		this.registryComponent = registry;
		this.serializerComponent = serializer;
		this.volatileComponent = volatileSerializer;
	}
	
	public IOManagerDefault(IORegistryDefault registry)
	{
		this(registry, new IOSerializerDefault(registry), new IOSerializerVolatileDefault(registry));
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
	public IOSerializerVolatile getVolatileSerializer()
	{
		return this.volatileComponent;
	}

}
