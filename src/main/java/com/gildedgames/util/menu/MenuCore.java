package com.gildedgames.util.menu;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.menu.client.IMenu;

import com.gildedgames.util.menu.client.MenuClientEvents;
import com.gildedgames.util.menu.client.util.MenuMinecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class MenuCore implements ICore
{
	public static final MenuCore INSTANCE = new MenuCore();

	public static final IMenu MINECRAFT_MENU = new MenuMinecraft();

	private final SidedObject<MenuServices> serviceLocator = new SidedObject<>(new MenuServices(), new MenuServices());

	public MenuCore()
	{

	}

	@Override
	public void flushData()
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.registerEventHandler(new MenuClientEvents());

		MenuCore.INSTANCE.registerMenu(MINECRAFT_MENU);
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

}
