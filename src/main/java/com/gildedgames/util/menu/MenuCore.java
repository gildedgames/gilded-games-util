package com.gildedgames.util.menu;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.menu.client.IMenu;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class MenuCore implements ICore
{
	
	public static final MenuCore INSTANCE = new MenuCore();
	
	private SidedObject<MenuServices> serviceLocator = new SidedObject<MenuServices>(new MenuServices(), new MenuServices());

	public MenuCore()
	{
		
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		
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
	
	public static MenuServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}
	
	public void registerMenu(IMenu menu)
	{
		this.serviceLocator.client().registerMenu(menu);
	}

	@Override
	public void flushData()
	{
		
	}

}
