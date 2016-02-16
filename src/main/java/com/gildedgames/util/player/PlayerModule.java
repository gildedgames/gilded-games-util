package com.gildedgames.util.player;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerEventHandler;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHook;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookClient;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.server.PlayerHookSaveHandler;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerModule extends Module
{

	public static final PlayerModule INSTANCE = new PlayerModule();

	public final PlayerHookSaveHandler playerHookSaveHandler = new PlayerHookSaveHandler();

	public final PlayerEventHandler playerEventHandler = new PlayerEventHandler();

	private final SidedObject<PlayerServices> serviceLocator = new SidedObject<>(new PlayerServices(), new PlayerServices());

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilModule.NETWORK.registerMessage(MessagePlayerHook.Handler.class, MessagePlayerHook.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(MessagePlayerHookClient.Handler.class, MessagePlayerHookClient.class, Side.SERVER);
		UtilModule.NETWORK.registerMessage(MessagePlayerHookRequest.Handler.class, MessagePlayerHookRequest.class, Side.SERVER);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		UtilModule.registerEventHandler(this.playerHookSaveHandler);
		UtilModule.registerEventHandler(this.playerEventHandler);
	}

	@Override
	public void flushData()
	{
		this.playerHookSaveHandler.flushData();
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (IPlayerHookPool<?> manager : PlayerModule.locate().getPools())
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
