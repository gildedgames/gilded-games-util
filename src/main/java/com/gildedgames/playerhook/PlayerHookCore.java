package com.gildedgames.playerhook;

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

	/**
	 * Returns the game's current networking Side.
	 */
	public static Side getAppState()
	{
		return FMLCommonHandler.instance().getEffectiveSide();
	}

	/**
	 * Returns whether the game's state is Client-Side or not.
	 */
	public static boolean isClient()
	{
		return getAppState() == Side.CLIENT;
	}

	/**
	 * Returns whether the game's state is Server-Side or not.
	 */
	public static boolean isServer()
	{
		return getAppState() == Side.SERVER;
	}
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(PlayerHookCore.MOD_ID);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		this.proxy.init();
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		
	}
	
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		
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

