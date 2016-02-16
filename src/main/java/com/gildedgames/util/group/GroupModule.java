package com.gildedgames.util.group;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.nbt.NBTBridge;
import com.gildedgames.util.core.util.GGHelper;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupInfo;
import com.gildedgames.util.group.common.core.GroupPool;
import com.gildedgames.util.group.common.core.MemberData;
import com.gildedgames.util.group.common.core.PacketAddGroup;
import com.gildedgames.util.group.common.core.PacketAddInvite;
import com.gildedgames.util.group.common.core.PacketAddMember;
import com.gildedgames.util.group.common.core.PacketChangeGroupInfo;
import com.gildedgames.util.group.common.core.PacketChangeOwner;
import com.gildedgames.util.group.common.core.PacketGroupPool;
import com.gildedgames.util.group.common.core.PacketInvite;
import com.gildedgames.util.group.common.core.PacketJoin;
import com.gildedgames.util.group.common.core.PacketRemoveGroup;
import com.gildedgames.util.group.common.core.PacketRemoveInvitation;
import com.gildedgames.util.group.common.core.PacketRemoveInvite;
import com.gildedgames.util.group.common.core.PacketRemoveMember;
import com.gildedgames.util.group.common.notifications.NotificationMessageInvited;
import com.gildedgames.util.group.common.notifications.NotificationsPoolHook;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.player.PlayerModule;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.relauncher.Side;

public class GroupModule extends Module
{

	public final static GroupModule INSTANCE = new GroupModule();

	private final SidedObject<GroupServices> serviceLocator = new SidedObject<>(new GroupServices(Side.CLIENT), new GroupServices(Side.SERVER));

	public static GroupServices locate()
	{
		return GroupModule.INSTANCE.serviceLocator.instance();
	}

	private static GroupServices client()
	{
		return GroupModule.INSTANCE.serviceLocator.client();
	}

	private static GroupServices server()
	{
		return GroupModule.INSTANCE.serviceLocator.server();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		PlayerModule.INSTANCE.registerPlayerPool(this.serviceLocator.client().getPlayers(), this.serviceLocator.server().getPlayers());

		GroupPool client = this.serviceLocator.client().getDefaultPool();

		client.addListener(new NotificationsPoolHook());

		client().registerPool(client);
		GroupPool server = this.serviceLocator.server().getDefaultPool();

		server().registerPool(server);

		UtilModule.NETWORK.registerPacket(PacketAddGroup.class);
		UtilModule.NETWORK.registerPacket(PacketAddInvite.class);
		UtilModule.NETWORK.registerPacket(PacketAddMember.class);
		UtilModule.NETWORK.registerPacket(PacketChangeGroupInfo.class);
		UtilModule.NETWORK.registerPacket(PacketChangeOwner.class, Side.SERVER);
		UtilModule.NETWORK.registerPacket(PacketInvite.class, Side.CLIENT);
		UtilModule.NETWORK.registerPacket(PacketJoin.class, Side.CLIENT);
		UtilModule.NETWORK.registerPacket(PacketRemoveGroup.class);
		UtilModule.NETWORK.registerPacket(PacketRemoveInvitation.class, Side.CLIENT);
		UtilModule.NETWORK.registerPacket(PacketRemoveInvite.class);
		UtilModule.NETWORK.registerPacket(PacketRemoveMember.class);
		UtilModule.NETWORK.registerPacket(PacketGroupPool.class, Side.CLIENT);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		IORegistry registry = UtilModule.locate().getIORegistry();
		registry.registerClass(Group.class, 6495);
		registry.registerClass(GroupInfo.class, 6496);
		registry.registerClass(MemberData.class, 6497);
		registry.registerClass(GroupPermsDefault.class, 6499);
		registry.registerClass(GroupMember.class, 6500);
		registry.registerClass(NotificationMessageInvited.class, 6502);
	}

	@Override
	public void flushData()
	{
		for (GroupPool pool : this.serviceLocator.server().getPools())
		{
			NBTTagCompound tag = new NBTTagCompound();
			NBTBridge bridge = new NBTBridge(tag);
			pool.write(bridge);
			GGHelper.writeNBTToFile(tag, pool.getID() + ".group");
		}
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		for (GroupPool pool : this.serviceLocator.server().getPools())
		{
			pool.clear();
		}
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{
		for (GroupPool pool : this.serviceLocator.server().getPools())
		{
			NBTTagCompound tag = GGHelper.readNBTFromFile(pool.getID() + ".group");
			if (tag == null)
			{
				continue;
			}
			NBTBridge bridge = new NBTBridge(tag);
			pool.read(bridge);
		}
	}

}
