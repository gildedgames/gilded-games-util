package com.gildedgames.util.io_manager.overhead;

public interface IOManager
{

	IORegistry getRegistry();

	IOFileController getFileController();

	IOSerializer getSerializer();

	IOVolatileController getVolatileController();

	String getID();

}
