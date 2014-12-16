package com.gildedgames.playerhook;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.common.PlayerEventHandler;
import com.gildedgames.playerhook.common.PlayerHookManager;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerHook;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerHookClient;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.playerhook.server.PlayerHookSaveHandler;
import com.gildedgames.playerhook.server.ServerProxy;
	
@Mod(modid = PlayerHookCore.MOD_ID, name = "Player Hook Utility", version = PlayerHookCore.VERSION)
public class PlayerHookCore
{
	
	public static final String MOD_ID = "player-hook-util";
	
	public static final String VERSION = "1.8-1.0";
	
	@Instance(PlayerHookCore.MOD_ID)
	public static PlayerHookCore instance;
	
	@SidedProxy(clientSide = "com.gildedgames.playerhook.client.ClientProxy", serverSide = "com.gildedgames.playerhook.server.ServerProxy")
	public static ServerProxy proxy;

	public static SimpleNetworkWrapper NETWORK;
	
	public PlayerHookSaveHandler playerHookSaveHandler = new PlayerHookSaveHandler();
	
	public PlayerEventHandler playerEventHandler = new PlayerEventHandler();
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(PlayerHookCore.MOD_ID);
		
		NETWORK.registerMessage(MessagePlayerHook.Handler.class, MessagePlayerHook.class, 0, Side.CLIENT);
		NETWORK.registerMessage(MessagePlayerHookClient.Handler.class, MessagePlayerHookClient.class, 1, Side.SERVER);
		NETWORK.registerMessage(MessagePlayerHookRequest.Handler.class, MessagePlayerHookRequest.class, 2, Side.SERVER);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		this.proxy.init();

		MinecraftForge.EVENT_BUS.register(this.playerHookSaveHandler);
		FMLCommonHandler.instance().bus().register(this.playerHookSaveHandler);

		MinecraftForge.EVENT_BUS.register(this.playerEventHandler);
		FMLCommonHandler.instance().bus().register(this.playerEventHandler);
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		
	}
	
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		this.playerHookSaveHandler.flushData();

		for (PlayerHookManager manager : PlayerHookManager.getManagers())
		{
			if (manager != null)
			{
				manager.clear();
			}
			else
			{
				// TO-DO: error log here, manager should never be null
			}
		}
	}

	/**
	 * Used to represent that a particular ResourceLocation is assigned to the mod.
	 * @return The appropriate address for the mod
	 */
	public static String modAddress()
	{
		return PlayerHookCore.MOD_ID + ":";
	}
	
}

