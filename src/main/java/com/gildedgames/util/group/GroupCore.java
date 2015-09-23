package com.gildedgames.util.group;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.group.common.core.GroupPool;
import com.gildedgames.util.group.common.notifications.NotificationsPoolHook;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.player.PlayerCore;

public class GroupCore implements ICore
{

	public final static GroupCore INSTANCE = new GroupCore();

	private final SidedObject<GroupServices> serviceLocator = new SidedObject<GroupServices>(new GroupServices(Side.CLIENT), new GroupServices(Side.SERVER));

	public static GroupServices locate()
	{
		return GroupCore.INSTANCE.serviceLocator.instance();
	}

	private static GroupServices client()
	{
		return GroupCore.INSTANCE.serviceLocator.client();
	}

	private static GroupServices server()
	{
		return GroupCore.INSTANCE.serviceLocator.server();
	}

	/**
	 * Register a new GroupPool.
	 * @param client The GroupPool the client should use. You'll 
	 * almost always want to use GroupPoolClient
	 * @param server The GroupPool the server should use. You'll
	 * almost always want to use GroupPoolServer
	 */
	public static void registerGroupPool(GroupPool client, GroupPool server)
	{
		client().registerPool(client);
		server().registerPool(server);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		PlayerCore.INSTANCE.registerPlayerPool(this.serviceLocator.client().getPlayers(), this.serviceLocator.server().getPlayers());

		GroupPool client = this.serviceLocator.client().getDefaultPool();
		GroupPool server = this.serviceLocator.server().getDefaultPool();

		client.addListener(new NotificationsPoolHook());

		registerGroupPool(client, server);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

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

}
