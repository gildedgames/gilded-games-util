package com.gildedgames.util.io_manager.overhead;

public interface IOManager
{

	IORegistry getRegistry();

	IOSerializer getSerializer();

	IOSerializerInternal getInternalSerializer();

	IOSerializerVolatile getVolatileSerializer();

	String getID();

}
