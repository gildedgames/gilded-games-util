package com.gildedgames.util.group;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;
import com.gildedgames.util.group.common.core.GroupPool;
import com.gildedgames.util.group.common.core.MemberData;
import com.gildedgames.util.group.common.core.PacketAddGroup;
import com.gildedgames.util.group.common.core.PacketAddInvite;
import com.gildedgames.util.group.common.core.PacketAddMember;
import com.gildedgames.util.group.common.core.PacketChangeGroupInfo;
import com.gildedgames.util.group.common.core.PacketChangeOwner;
import com.gildedgames.util.group.common.core.PacketInvite;
import com.gildedgames.util.group.common.core.PacketJoin;
import com.gildedgames.util.group.common.core.PacketRemoveGroup;
import com.gildedgames.util.group.common.core.PacketRemoveInvitation;
import com.gildedgames.util.group.common.core.PacketRemoveInvite;
import com.gildedgames.util.group.common.core.PacketRemoveMember;
import com.gildedgames.util.group.common.notifications.NotificationInvited;
import com.gildedgames.util.group.common.notifications.NotificationsPoolHook;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.player.PlayerCore;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

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

		UtilCore.NETWORK.registerPacket(PacketAddGroup.class);
		UtilCore.NETWORK.registerPacket(PacketAddInvite.class);
		UtilCore.NETWORK.registerPacket(PacketAddMember.class);
		UtilCore.NETWORK.registerPacket(PacketChangeGroupInfo.class, Side.CLIENT);
		UtilCore.NETWORK.registerPacket(PacketChangeOwner.class, Side.SERVER);
		UtilCore.NETWORK.registerPacket(PacketInvite.class, Side.CLIENT);
		UtilCore.NETWORK.registerPacket(PacketJoin.class, Side.CLIENT);
		UtilCore.NETWORK.registerPacket(PacketRemoveGroup.class);
		UtilCore.NETWORK.registerPacket(PacketRemoveInvitation.class, Side.CLIENT);
		UtilCore.NETWORK.registerPacket(PacketRemoveInvite.class);
		UtilCore.NETWORK.registerPacket(PacketRemoveMember.class);

	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		IORegistry registry = UtilCore.locate().getIORegistry();
		registry.registerClass(Group.class, 6495);
		registry.registerClass(GroupInfo.class, 6496);
		registry.registerClass(MemberData.class, 6497);
		registry.registerClass(NotificationInvited.class, 6498);
		registry.registerClass(GroupPermsDefault.class, 6499);
		registry.registerClass(GroupMember.class, 6500);
	}

	@Override
	public void flushData()
	{
		// TODO Auto-generated method stub

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
