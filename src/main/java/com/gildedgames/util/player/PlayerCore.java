package com.gildedgames.util.player;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerEventHandler;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHook;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookClient;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.server.PlayerHookSaveHandler;

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
import net.minecraftforge.fml.relauncher.Side;

public class PlayerCore implements ICore
{

	public static final PlayerCore INSTANCE = new PlayerCore();

	public final PlayerHookSaveHandler playerHookSaveHandler = new PlayerHookSaveHandler();

	public final PlayerEventHandler playerEventHandler = new PlayerEventHandler();

	private final SidedObject<PlayerServices> serviceLocator = new SidedObject<PlayerServices>(new PlayerServices(), new PlayerServices());

	private PlayerCore()
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
	public void flushData()
	{
		this.playerHookSaveHandler.flushData();
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (IPlayerHookPool<?> manager : PlayerCore.locate().getPools())
		{
			if (manager != null)
			{
				manager.clear();
			}
			else
			{
				/** TODO: error log here, manager should never be null **/
			}
		}
	}

	public static PlayerServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public <T extends IPlayerHook> void registerPlayerPool(IPlayerHookPool<T> client, IPlayerHookPool<T> server)
	{
		INSTANCE.serviceLocator.client().registerPlayerHookPool(client);
		INSTANCE.serviceLocator.server().registerPlayerHookPool(server);
	}

}
