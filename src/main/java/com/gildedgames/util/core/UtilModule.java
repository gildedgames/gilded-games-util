package com.gildedgames.util.core;

import com.gildedgames.util.chunk.ChunkModule;
import com.gildedgames.util.core.io.MCSyncableDispatcher;
import com.gildedgames.util.core.io.NetworkWrapper;
import com.gildedgames.util.group.GroupModule;
import com.gildedgames.util.instances.InstanceModule;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IOManagerTakenException;
import com.gildedgames.util.menu.MenuModule;
import com.gildedgames.util.notifications.NotificationModule;
import com.gildedgames.util.player.PlayerModule;
import com.gildedgames.util.spawning.SpawningModule;
import com.gildedgames.util.tab.TabModule;
import com.gildedgames.util.world.WorldModule;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod(modid = UtilModule.MOD_ID, name = "Gilded Games Utility", version = UtilModule.VERSION, dependencies = "before:*")
public class UtilModule extends Module
{

	public static final String MOD_ID = "gildedgamesutil";

	public static final String VERSION = "1.8-r1";

	public static final boolean DEBUG_MODE = true;

	@Instance(UtilModule.MOD_ID)
	public static UtilModule instance;

	@SidedProxy(clientSide = "com.gildedgames.util.core.client.ClientProxy", serverSide = "com.gildedgames.util.core.ServerProxy")
	public static ServerProxy proxy;

	public static final NetworkWrapper NETWORK = new NetworkWrapper();

	private final List<Module> modules = new ArrayList<>();

	private final SidedObject<UtilServices> serviceLocator;

	private final MCSyncableDispatcher syncableDispatcher;

	public UtilModule()
	{
		this.registerCore(PlayerModule.INSTANCE);
		this.registerCore(WorldModule.INSTANCE);
		this.registerCore(TabModule.INSTANCE);
		this.registerCore(GroupModule.INSTANCE);
		this.registerCore(new SpawningModule());
		this.registerCore(InstanceModule.INSTANCE);
		this.registerCore(NotificationModule.INSTANCE);
		this.registerCore(ChunkModule.INSTANCE);

		if (UtilModule.isClient())
		{
			this.registerCore(MenuModule.INSTANCE);
		}

		UtilServices clientLocator = new UtilServices();
		UtilServices serverLocator = new UtilServices();

		this.serviceLocator = new SidedObject<>(clientLocator, serverLocator);
		this.syncableDispatcher = new MCSyncableDispatcher("GildedGamesUtil");
	}

	public void registerCore(Module module)
	{
		this.modules.add(module);
	}

	public static ItemStack getItemStack(Block block)
	{
		return UtilModule.getItemStack(block, 1);
	}

	public static ItemStack getItemStack(Block block, int amount)
	{
		return new ItemStack(block, amount);
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		try
		{
			IOCore.io().registerManager(UtilModule.locate().getIOManager());
			IOCore.io().registerDispatcher(this.syncableDispatcher);
		}
		catch (IOManagerTakenException e)
		{
			e.printStackTrace();
		}

		UtilModule.NETWORK.init(UtilModule.MOD_ID);

		for (Module module : this.modules)
		{
			module.preInit(event);
		}

		proxy.preInit(event);
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		for (Module module : this.modules)
		{
			module.init(event);
		}

		proxy.init(event);
	}

	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		for (Module module : this.modules)
		{
			module.postInit(event);
		}

		proxy.postInit(event);
	}

	@Override
	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverAboutToStart(event);
		}

		proxy.serverAboutToStart(event);
	}

	@Override
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStarting(event);
		}

		proxy.serverStarting(event);
	}

	@Override
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStarted(event);
		}

		proxy.serverStarted(event);
	}

	@Override
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStopping(event);
		}

		proxy.serverStopping(event);

		this.flushData();
	}

	@Override
	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStopped(event);
		}

		proxy.serverStopped(event);
	}

	@Override
	public void flushData()
	{
		UtilModule.debugPrint("Flushing data for GG Util");
		for (Module module : this.modules)
		{
			module.flushData();
		}
		proxy.flushData();
		UtilModule.debugPrint("Finished flushing data for GG Util");
	}

	public MCSyncableDispatcher getDispatcher()
	{
		return this.syncableDispatcher;
	}

	public static UtilServices locate()
	{
		return instance.serviceLocator.instance();
	}

	public static String modAddress()
	{
		return UtilModule.MOD_ID + ":";
	}

	public static boolean isClient()
	{
		return getSide().isClient();
	}

	public static boolean isServer()
	{
		return getSide().isServer();
	}

	public static Side getSide()
	{
		Thread thr = Thread.currentThread();

		if (thr.getName().equals("Server thread") || thr.getName().startsWith("Netty Server IO"))
		{
			return Side.SERVER;
		}

		return Side.CLIENT;
	}

	public static void debugPrint(Object line)
	{
		if (DEBUG_MODE && line != null)
		{
			System.out.println("[GG DEV]: " + line.toString());
		}
	}

	public static void print(Object line)
	{
		if (line != null)
		{
			System.out.println("[GG]: " + line.toString());
		}
	}

	public static List<EntityPlayerMP> getOnlinePlayers()
	{
		return MinecraftServer.getServer().getConfigurationManager().playerEntityList;
	}

	public static EntityPlayer getPlayerOnServerFromUUID(UUID uuid)
	{
		if (uuid == null)
		{
			return null;
		}

		List<EntityPlayerMP> allPlayers = UtilModule.getOnlinePlayers();

		for (EntityPlayerMP player : allPlayers)
		{
			if (player.getUniqueID().equals(uuid))
			{
				return player;
			}
		}

		return null;
	}

	public static File getWorldDirectory()
	{
		String path = ".";

		if (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers != null && MinecraftServer.getServer().worldServers[0] != null)
		{
			path = MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", "");
		}

		return new File(path.replace("\\data", ""));
	}

	public static String getMinecraftDirectory()
	{
		return MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", "");
	}

	public static String translate(String key)
	{
		//TOOD: Maybe put "ggUtil." before the key?
		return StatCollector.translateToLocal(key);
	}

	public static void registerEventHandler(Object o)
	{
		MinecraftForge.EVENT_BUS.register(o);
	}

}
