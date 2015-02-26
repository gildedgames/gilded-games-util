package com.gildedgames.util.world;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.world.common.IWorldHook;
import com.gildedgames.util.world.common.IWorldHookPool;
import com.gildedgames.util.world.common.WorldEventHandler;
import com.gildedgames.util.world.common.world.IWorld;
import com.gildedgames.util.world.common.world.WorldMinecraftFactoryClient;
import com.gildedgames.util.world.common.world.WorldMinecraftFactoryServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;

public class WorldCore implements ICore
{

	public static final WorldCore INSTANCE = new WorldCore();

	//Change the factories here to use different IWorlds throughout the workspace
	private final SidedObject<WorldServices> serviceLocator;

	private final WorldEventHandler worldEventHandler = new WorldEventHandler();

	public WorldCore()
	{
		WorldServices clientLocator = null;
		
		if (UtilCore.isServer())
		{
			clientLocator = new WorldServices(false, new WorldMinecraftFactoryServer());
		}
		else
		{
			clientLocator = new WorldServices(true, new WorldMinecraftFactoryClient());
		}
		
		WorldServices serverLocator = new WorldServices(false, new WorldMinecraftFactoryServer());
		
		this.serviceLocator = new SidedObject<WorldServices>(clientLocator, serverLocator);
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

	/**
	 * Util method for accessing IWorlds through the Minecraft World instance.
	 * Only use where you are sure you can use World instances.
	 */
	public static IWorld get(World world)
	{
		return get(world.provider.getDimensionId());
	}

	public static IWorld get(int dimId)
	{
		return locate().getWrapper(dimId);
	}

	public static WorldServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public <T extends IWorldHook> void registerWorldPool(IWorldHookPool<T> client, IWorldHookPool<T> server)
	{
		INSTANCE.serviceLocator.client().registerWorldPool(client);
		INSTANCE.serviceLocator.server().registerWorldPool(server);
	}

}
