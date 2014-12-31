package com.gildedgames.util.worldhook;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.worldhook.common.IWorldPool;
import com.gildedgames.util.worldhook.common.WorldEventHandler;
import com.gildedgames.util.worldhook.common.WorldPool;
import com.gildedgames.util.worldhook.common.test.WorldTest;
import com.gildedgames.util.worldhook.common.test.WorldTestFactory;

public class WorldHookCore implements ICore
{
	
	public static final WorldHookCore INSTANCE = new WorldHookCore();
	
	private SidedObject<WorldHookServices> serviceLocator = new SidedObject<WorldHookServices>(new WorldHookServices(), new WorldHookServices());
	
	private WorldEventHandler worldEventHandler = new WorldEventHandler();
	
	private WorldPool<WorldTest> worldPool = new WorldPool<WorldTest>(new WorldTestFactory());
	
	public WorldHookCore()
	{
		
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		registerWorldPool(this.worldPool);
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
		for (IWorldPool worldPool : WorldHookCore.locate().getPools())
		{
			if (worldPool != null)
			{
				worldPool.clear();
			}
			else
			{
				/** TO-DO: error log here, manager should never be null **/
			}
		}
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
