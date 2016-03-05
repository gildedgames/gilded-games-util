package com.gildedgames.util.modules.group;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.nbt.NBTBridge;
import com.gildedgames.util.core.util.GGHelper;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.core.GroupInfo;
import com.gildedgames.util.modules.group.common.core.GroupPool;
import com.gildedgames.util.modules.group.common.core.MemberData;
import com.gildedgames.util.modules.group.common.core.PacketAddGroup;
import com.gildedgames.util.modules.group.common.core.PacketAddInvite;
import com.gildedgames.util.modules.group.common.core.PacketAddMember;
import com.gildedgames.util.modules.group.common.core.PacketChangeGroupInfo;
import com.gildedgames.util.modules.group.common.core.PacketChangeOwner;
import com.gildedgames.util.modules.group.common.core.PacketGroupPool;
import com.gildedgames.util.modules.group.common.core.PacketInvite;
import com.gildedgames.util.modules.group.common.core.PacketJoin;
import com.gildedgames.util.modules.group.common.core.PacketRemoveGroup;
import com.gildedgames.util.modules.group.common.core.PacketRemoveInvitation;
import com.gildedgames.util.modules.group.common.core.PacketRemoveInvite;
import com.gildedgames.util.modules.group.common.core.PacketRemoveMember;
import com.gildedgames.util.modules.group.common.notifications.NotificationMessageInvited;
import com.gildedgames.util.modules.group.common.notifications.NotificationsPoolHook;
import com.gildedgames.util.modules.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.modules.group.common.player.GroupMember;
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
		EntityHookModule.api().registerHookProvider(GroupMember.PROVIDER);

		GroupPool client = this.serviceLocator.client().getDefaultPool();

		client.addListener(new NotificationsPoolHook());

		client().registerPool(client);
		GroupPool server = this.serviceLocator.server().getDefaultPool();

		server().registerPool(server);

		UtilModule.NETWORK.registerMessage(PacketAddGroup.HandlerClient.class, PacketAddGroup.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketAddGroup.HandlerServer.class, PacketAddGroup.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketAddInvite.HandlerClient.class, PacketAddInvite.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketAddInvite.HandlerServer.class, PacketAddInvite.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketAddMember.HandlerClient.class, PacketAddMember.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketAddMember.HandlerServer.class, PacketAddMember.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketChangeGroupInfo.HandlerClient.class, PacketChangeGroupInfo.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketChangeGroupInfo.HandlerServer.class, PacketChangeGroupInfo.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketChangeOwner.HandlerServer.class, PacketChangeOwner.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketInvite.HandlerClient.class, PacketInvite.class, Side.CLIENT);

		UtilModule.NETWORK.registerMessage(PacketJoin.HandlerClient.class, PacketJoin.class, Side.CLIENT);

		UtilModule.NETWORK.registerMessage(PacketRemoveGroup.HandlerClient.class, PacketRemoveGroup.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketRemoveGroup.HandlerServer.class, PacketRemoveGroup.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketRemoveInvitation.HandlerClient.class, PacketRemoveInvitation.class, Side.CLIENT);

		UtilModule.NETWORK.registerMessage(PacketRemoveInvite.HandlerClient.class, PacketRemoveInvite.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketRemoveInvite.HandlerServer.class, PacketRemoveInvite.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketRemoveMember.HandlerClient.class, PacketRemoveMember.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketRemoveMember.HandlerServer.class, PacketRemoveMember.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketGroupPool.Handler.class, PacketGroupPool.class, Side.CLIENT);
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
