package com.gildedgames.util.core.client;

import com.gildedgames.util.core.ServerProxy;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.tab.TabModule;
import org.lwjgl.input.Keyboard;

import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.tab.client.TabClientEvents;
import com.gildedgames.util.tab.client.social.TabChat;
import com.gildedgames.util.tab.client.social.TabGroup;
import com.gildedgames.util.tab.client.social.TabNotifications;
import com.gildedgames.util.tab.common.TabApiImpl;
import com.gildedgames.util.tab.client.inventory.TabBackpack;
import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.tab.common.util.TabGroupHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

//import com.gildedgames.util.ui.TestTab;

public class ClientProxy extends ServerProxy
{

	private final Minecraft mc = Minecraft.getMinecraft();

	public static final KeyBinding keyBindHopUniverse = new KeyBinding(StatCollector.translateToLocal("keybindings.hopUniverse"), Keyboard.KEY_H, "key.categories.misc");

	public static final ITab GROUP_TAB = new TabGroup();

	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		UtilClientEvents utilEvents = new UtilClientEvents();
		UtilModule.registerEventHandler(utilEvents);

		UtilModule.registerEventHandler(MinecraftGuiViewer.instance().getTickInfo());

		TabClientEvents clientEvents = new TabClientEvents();
		UtilModule.registerEventHandler(clientEvents);

		TabGroupHandler socialTab = new TabGroupHandler();
		socialTab.getSide(Side.CLIENT).add(new TabChat());
		socialTab.getSide(Side.CLIENT).add(GROUP_TAB);
		socialTab.getSide(Side.CLIENT).add(new TabNotifications());

		TabModule.api().registerGroup(socialTab);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		ClientRegistry.registerKeyBinding(keyBindHopUniverse);

		//TabAPI.INSTANCE.getInventoryGroup().getSide(Side.CLIENT).add(new TestTab());
	}

	@Override
	public void addScheduledTask(Runnable runnable)
	{
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}

}
