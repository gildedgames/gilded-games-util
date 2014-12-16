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
import com.gildedgames.playerhook.common.player.IPlayerHook;
import com.gildedgames.playerhook.common.util.NBTDataHandler;
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
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
	{
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(PlayerHookCore.MOD_ID);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		this.proxy.init();
		
		PlayerEventHandler playerEventHandler = new PlayerEventHandler();
		
		MinecraftForge.EVENT_BUS.register(playerEventHandler);
		FMLCommonHandler.instance().bus().register(playerEventHandler);
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		flushDataIn();
	}
	
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		flushDataOut();

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
	
	public static void flushDataIn()
	{
		NBTDataHandler dataHandler = new NBTDataHandler();

		for (PlayerHookManager manager : PlayerHookManager.getManagers())
		{
			manager.instance(Side.SERVER).setPlayers(dataHandler.load(IPlayerHook.class, "players.dat"));
		}
	}
	
	public static void flushDataOut()
	{
		NBTDataHandler dataHandler = new NBTDataHandler();

		for (PlayerHookManager manager : PlayerHookManager.getManagers())
		{
			dataHandler.save("players.dat", manager.instance(Side.SERVER).getPlayers());
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

