package com.gildedgames.util.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
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

import org.apache.logging.log4j.Logger;

import com.gildedgames.util.core.io.MCSyncableDispatcher;
import com.gildedgames.util.core.io.NetworkWrapper;
import com.gildedgames.util.io.ClassSerializer;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IOManagerTakenException;
import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.instances.InstanceModule;
import com.gildedgames.util.modules.menu.MenuModule;
import com.gildedgames.util.modules.notifications.NotificationModule;
import com.gildedgames.util.modules.spawning.SpawningModule;
import com.gildedgames.util.modules.tab.TabModule;
import com.gildedgames.util.modules.world.WorldModule;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

@Mod(modid = UtilModule.MOD_ID, name = "Gilded Games Utility", version = UtilModule.VERSION, dependencies = "before:*")
public class UtilModule
{
	public static final String MOD_ID = "gildedgamesutil";

	public static final String VERSION = "1.8.9-r1";

	public static final NetworkWrapper NETWORK = new NetworkWrapper();

	@Instance(UtilModule.MOD_ID)
	public static UtilModule instance;

	@SidedProxy(clientSide = "com.gildedgames.util.core.client.ClientProxy", serverSide = "com.gildedgames.util.core.ServerProxy")
	public static ServerProxy proxy;

	public static Logger logger;

	private final List<Module> modules = new ArrayList<>();

	private UtilServices services = new UtilServices();

	private MCSyncableDispatcher syncableDispatcher;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilModule.logger = event.getModLog();

		this.registerModule(EntityHookModule.INSTANCE);
		this.registerModule(WorldModule.INSTANCE);
		this.registerModule(InstanceModule.INSTANCE);
		this.registerModule(GroupModule.INSTANCE);
		this.registerModule(TabModule.INSTANCE);
		this.registerModule(SpawningModule.INSTANCE);
		this.registerModule(NotificationModule.INSTANCE);
		this.registerModule(ChunkModule.INSTANCE);
		this.registerModule(InstanceModule.INSTANCE);

		if (UtilModule.isClient())
		{
			this.registerModule(MenuModule.INSTANCE);
		}

		this.syncableDispatcher = new MCSyncableDispatcher("GildedGamesUtil");

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

	public void registerModule(Module module)
	{
		UtilModule.logger().debug("Adding module " + module.getClass().getName());

		this.modules.add(module);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		for (Module module : this.modules)
		{
			module.init(event);
		}

		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		for (Module module : this.modules)
		{
			module.postInit(event);
		}

		proxy.postInit(event);
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverAboutToStart(event);
		}

		proxy.serverAboutToStart(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStarting(event);
		}

		proxy.serverStarting(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStarted(event);
		}

		proxy.serverStarted(event);
	}

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

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (Module module : this.modules)
		{
			module.serverStopped(event);
		}

		proxy.serverStopped(event);
	}

	public void flushData()
	{
		for (Module module : this.modules)
		{
			module.flushData();
		}

		proxy.flushData();
	}

	public MCSyncableDispatcher getDispatcher()
	{
		return this.syncableDispatcher;
	}

	public static UtilServices locate()
	{
		return instance.services;
	}

	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getSide().isClient();
	}

	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getSide().isServer();
	}

	public static Logger logger()
	{
		return instance.logger;
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

	public static void registerEventHandler(Object obj)
	{
		MinecraftForge.EVENT_BUS.register(obj);
	}

	public static boolean isInsideDevEnvironment()
	{
		return Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE;
	}
}
