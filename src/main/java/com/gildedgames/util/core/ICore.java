package com.gildedgames.util.core;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

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
