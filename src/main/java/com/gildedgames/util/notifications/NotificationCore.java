package com.gildedgames.util.notifications;

import java.util.UUID;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.notifications.common.core.INotification;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.notifications.common.networking.messages.PacketClickedResponse;
import com.gildedgames.util.notifications.common.networking.messages.PacketNotification;
import com.gildedgames.util.notifications.common.networking.messages.PacketRemoveMessage;
import com.gildedgames.util.notifications.common.player.PlayerNotification;
import com.gildedgames.util.notifications.common.util.DefaultMessage;
import com.gildedgames.util.notifications.common.util.DefaultNotification;
import com.gildedgames.util.notifications.common.util.MessageNotification;
import com.gildedgames.util.notifications.common.util.PopupNotification;
import com.gildedgames.util.player.PlayerCore;

import net.minecraft.entity.player.EntityPlayer;
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

public class NotificationCore implements ICore
{

	@SidedProxy(modId = UtilCore.MOD_ID, clientSide = "com.gildedgames.util.notifications.client.ClientProxy", serverSide = "com.gildedgames.util.notifications.CommonProxy")
	public static CommonProxy proxy;

	private SidedObject<NotificationServices> serviceLocator;

	public final static NotificationCore INSTANCE = new NotificationCore();

	public static NotificationServices locate()
	{
		return NotificationCore.INSTANCE.serviceLocator.instance();
	}

	public static PlayerNotification getPlayerNotifications(EntityPlayer player)
	{
		return NotificationCore.locate().getPlayers().get(player);
	}

	public static PlayerNotification getPlayerNotifications(UUID uuid)
	{
		return NotificationCore.locate().getPlayers().get(uuid);
	}

	public static void sendNotification(INotification notification)
	{
		locate().getDispatcher().sendNotification(notification);
	}

	public static void sendPopup(String message, UUID sender, UUID receiver)
	{
		locate().getDispatcher().sendNotification(new PopupNotification(message, sender, receiver));
	}

	public static void sendMessage(String title, String message, UUID sender, UUID receiver)
	{
		locate().getDispatcher().sendNotification(new MessageNotification(title, message, sender, receiver));
	}

	public static void sendNotification(INotificationMessage message)
	{
		locate().getDispatcher().sendNotification(new DefaultNotification(message));
	}

	public static EntityPlayer playerFromUUID(UUID uuid)
	{
		return getPlayerNotifications(uuid).getProfile().getEntity();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.serviceLocator = proxy.createServices();
		PlayerCore.INSTANCE.registerPlayerPool(this.serviceLocator.client().getPlayers(), this.serviceLocator.server().getPlayers());

		UtilCore.NETWORK.registerPacket(PacketNotification.class);
		UtilCore.NETWORK.registerPacket(PacketRemoveMessage.class);
		UtilCore.NETWORK.registerPacket(PacketClickedResponse.class, Side.SERVER);

		proxy.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		IORegistry registry = UtilCore.locate().getIORegistry();
		registry.registerClass(PopupNotification.class, 2345);
		registry.registerClass(DefaultMessage.class, 2346);
		registry.registerClass(DefaultNotification.class, 2347);
		registry.registerClass(MessageNotification.class, 2348);
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
