package com.gildedgames.util.core;

import net.minecraftforge.fml.common.event.*;

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
