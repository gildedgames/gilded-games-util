package com.gildedgames.util.playerhook;

import net.minecraftforge.common.MinecraftForge;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.PlayerEventHandler;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHook;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHookClient;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;
import com.gildedgames.util.playerhook.server.PlayerHookSaveHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.relauncher.Side;

public class PlayerHookCore implements ICore
{

	public static final PlayerHookCore INSTANCE = new PlayerHookCore();

	public PlayerHookSaveHandler playerHookSaveHandler = new PlayerHookSaveHandler();

	public PlayerEventHandler playerEventHandler = new PlayerEventHandler();

	private SidedObject<PlayerHookServices> serviceLocator = new SidedObject<PlayerHookServices>(new PlayerHookServices(), new PlayerHookServices());

	private PlayerHookCore()
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.NETWORK.registerMessage(MessagePlayerHook.Handler.class, MessagePlayerHook.class, Side.CLIENT);
		UtilCore.NETWORK.registerMessage(MessagePlayerHookClient.Handler.class, MessagePlayerHookClient.class, Side.SERVER);
		UtilCore.NETWORK.registerMessage(MessagePlayerHookRequest.Handler.class, MessagePlayerHookRequest.class, Side.SERVER);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this.playerHookSaveHandler);
		FMLCommonHandler.instance().bus().register(this.playerHookSaveHandler);

		MinecraftForge.EVENT_BUS.register(this.playerEventHandler);
		FMLCommonHandler.instance().bus().register(this.playerEventHandler);
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
	public void serverStarting(FMLServerStartingEvent event)
	{

	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{

	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{
		this.playerHookSaveHandler.flushData();

		for (IPlayerHookPool<?> manager : PlayerHookCore.locate().getPools())
		{
			if (manager != null)
			{
				manager.clear();
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

	public static PlayerHookServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public <T extends IPlayerHook> void registerPlayerPool(IPlayerHookPool<T> client, IPlayerHookPool<T> server)
	{
		INSTANCE.serviceLocator.client().registerPlayerHookPool(client);
		INSTANCE.serviceLocator.server().registerPlayerHookPool(server);
	}

}
