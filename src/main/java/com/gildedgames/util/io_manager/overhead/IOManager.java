package com.gildedgames.util.io_manager.overhead;

public interface IOManager
{

	IORegistry getRegistry();

	IOSerializerExternal getSerializer();

	IOSerializerVolatile getVolatileSerializer();

	String getID();

}
