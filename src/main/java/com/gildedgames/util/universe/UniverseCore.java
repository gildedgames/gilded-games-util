package com.gildedgames.util.universe;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.player.PlayerCore;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.networking.messages.MessageTravelUniverse;
import com.gildedgames.util.universe.common.util.TeleporterGeneric;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.relauncher.Side;

public class UniverseCore implements ICore
{

	public static UniverseCore INSTANCE = new UniverseCore();

	public static TeleporterGeneric GENERIC_TELEPORTER;

	private SidedObject<UniverseServices> serviceLocator = new SidedObject<UniverseServices>(new UniverseServices(Side.CLIENT), new UniverseServices(Side.SERVER));

	public static UniverseServices locate()
	{
		return UniverseCore.INSTANCE.serviceLocator.instance();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.NETWORK.registerMessage(MessageTravelUniverse.Handler.class, MessageTravelUniverse.class, Side.SERVER);

		UniverseAPI.instance().register(UniverseAPI.instance().getMinecraftUniverseID(), UniverseAPI.instance().getMinecraftUniverse());

		PlayerCore.INSTANCE.registerPlayerPool(this.serviceLocator.client().getPlayers(), this.serviceLocator.server().getPlayers());
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{
		GENERIC_TELEPORTER = new TeleporterGeneric(MinecraftServer.getServer().worldServerForDimension(0));
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

	public static void teleportToDimension(EntityPlayerMP player, int dimension)
	{
		ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
		scm.transferPlayerToDimension(player, dimension, UniverseCore.GENERIC_TELEPORTER);
	}

}
