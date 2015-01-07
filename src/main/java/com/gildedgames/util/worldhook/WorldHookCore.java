package com.gildedgames.util.worldhook;

import net.minecraftforge.common.MinecraftForge;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.worldhook.common.IWorldPool;
import com.gildedgames.util.worldhook.common.WorldEventHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class WorldHookCore implements ICore
{

	public static final WorldHookCore INSTANCE = new WorldHookCore();

	private SidedObject<WorldHookServices> serviceLocator = new SidedObject<WorldHookServices>(new WorldHookServices(), new WorldHookServices());

	private WorldEventHandler worldEventHandler = new WorldEventHandler();

	public WorldHookCore()
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this.worldEventHandler);
		FMLCommonHandler.instance().bus().register(this.worldEventHandler);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{

	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{

	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{

	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{

	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{

	}

	public static WorldHookServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public void registerWorldPool(IWorldPool worldPool)
	{
		INSTANCE.serviceLocator.client().registerWorldPool(worldPool);
		INSTANCE.serviceLocator.server().registerWorldPool(worldPool);
	}

}
