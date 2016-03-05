package com.gildedgames.util.modules.notifications;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;
import com.gildedgames.util.modules.notifications.common.core.INotification;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import com.gildedgames.util.modules.notifications.common.networking.messages.PacketClickedResponse;
import com.gildedgames.util.modules.notifications.common.networking.messages.PacketNotification;
import com.gildedgames.util.modules.notifications.common.networking.messages.PacketRemoveMessage;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;
import com.gildedgames.util.modules.notifications.common.util.DefaultMessage;
import com.gildedgames.util.modules.notifications.common.util.DefaultNotification;
import com.gildedgames.util.modules.notifications.common.util.MessageNotification;
import com.gildedgames.util.modules.notifications.common.util.PopupNotification;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.UUID;

public class NotificationModule extends Module
{

	@SidedProxy(modId = UtilModule.MOD_ID, clientSide = "com.gildedgames.util.modules.notifications.client.ClientProxy", serverSide = "com.gildedgames.util.modules.notifications.CommonProxy")
	public static CommonProxy proxy;

	private SidedObject<NotificationServices> serviceLocator;

	private PlayerHookProvider<PlayerNotification> hookProvider;

	public final static NotificationModule INSTANCE = new NotificationModule();

	public static NotificationServices locate()
	{
		return NotificationModule.INSTANCE.serviceLocator.instance();
	}

	public static PlayerNotification getPlayerNotifications(EntityPlayer player)
	{
		return NotificationModule.INSTANCE.hookProvider.getHook(player);
	}

	public static PlayerNotification getPlayerNotifications(UUID uuid)
	{
		return NotificationModule.INSTANCE.hookProvider.getHook(uuid);
	}

	public static Collection<PlayerNotification> getAllPlayerNotifications()
	{
		return NotificationModule.INSTANCE.hookProvider.getPool().getAttachedHooks();
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

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		this.serviceLocator = proxy.createServices();

		this.hookProvider = new PlayerHookProvider<>("util:notifications", new PlayerNotification.Factory());

		EntityHookModule.api().registerHookProvider(this.hookProvider);

		UtilModule.NETWORK.registerMessage(PacketNotification.HandlerClient.class, PacketNotification.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketNotification.HandlerServer.class, PacketNotification.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketRemoveMessage.HandlerClient.class, PacketRemoveMessage.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketRemoveMessage.HandlerServer.class, PacketRemoveMessage.class, Side.SERVER);

		UtilModule.NETWORK.registerMessage(PacketClickedResponse.HandlerServer.class, PacketClickedResponse.class, Side.SERVER);

		proxy.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		IORegistry registry = UtilModule.locate().getIORegistry();

		registry.registerClass(PopupNotification.class, 2345);
		registry.registerClass(DefaultMessage.class, 2346);
		registry.registerClass(DefaultNotification.class, 2347);
		registry.registerClass(MessageNotification.class, 2348);
	}
}
