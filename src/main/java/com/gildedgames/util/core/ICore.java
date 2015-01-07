package com.gildedgames.util.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public interface ICore
{

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	void serverAboutToStart(FMLServerAboutToStartEvent event);

	void serverStopping(FMLServerStoppingEvent event);

	void serverStopped(FMLServerStoppedEvent event);

	void serverStarting(FMLServerStartingEvent event);

	void serverStarted(FMLServerStartedEvent event);

}
