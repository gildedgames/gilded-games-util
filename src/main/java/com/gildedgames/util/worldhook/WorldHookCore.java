package com.gildedgames.util.worldhook;

import net.minecraft.world.World;
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
import com.gildedgames.util.worldhook.common.world.IWorld;

public class WorldHookCore implements ICore
{

	public static final WorldHookCore INSTANCE = new WorldHookCore();

	private SidedObject<WorldHookServices> serviceLocator = new SidedObject<WorldHookServices>(new WorldHookServices(true), new WorldHookServices(false));

	private WorldEventHandler worldEventHandler = new WorldEventHandler();

	private WorldPool<WorldTest> worldPool = new WorldPool<WorldTest>(new WorldTestFactory(), "test");

	public WorldHookCore()
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		//this.registerWorldPool(this.worldPool);
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

	/**
	 * Util method for accessing IWorlds through the Minecraft World instance.
	 * Only use where you are sure you can use World instances.
	 */
	public static IWorld getWrapper(World world)
	{
		return locate().getWrapper(world.provider.getDimensionId());
	}

	public static IWorld getWrapper(int dimId)
	{
		return locate().getWrapper(dimId);
	}

	public static WorldHookServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public void registerWorldPool(IWorldPool client, IWorldPool server)
	{
		INSTANCE.serviceLocator.client().registerWorldPool(client);
		INSTANCE.serviceLocator.server().registerWorldPool(server);
	}

}
