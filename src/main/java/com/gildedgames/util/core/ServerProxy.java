package com.gildedgames.util.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;

public class ServerProxy implements ICore
{
	
	public EntityPlayer getPlayer()
	{
		return null;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilEvents utilEvents = new UtilEvents();

		MinecraftForge.EVENT_BUS.register(utilEvents);
		FMLCommonHandler.instance().bus().register(utilEvents);
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

}
