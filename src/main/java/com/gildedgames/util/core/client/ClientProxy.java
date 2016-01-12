package com.gildedgames.util.core.client;

import com.gildedgames.util.core.ServerProxy;
import com.gildedgames.util.core.UtilCore;
import org.lwjgl.input.Keyboard;

import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.tab.client.TabClientEvents;
import com.gildedgames.util.tab.client.social.TabChat;
import com.gildedgames.util.tab.client.social.TabGroup;
import com.gildedgames.util.tab.client.social.TabNotifications;
import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.tab.common.util.TabGroupHandler;
import com.gildedgames.util.universe.client.gui.TabUniverseHopper;

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

	public static final ITab UNIVERSE_HOPPER_TAB = new TabUniverseHopper();

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
		UtilCore.registerEventHandler(utilEvents);

		UtilCore.registerEventHandler(MinecraftGuiViewer.instance().getTickInfo());

		TabClientEvents clientEvents = new TabClientEvents();
		UtilCore.registerEventHandler(clientEvents);

		TabAPI.setBackpackTab(new TabBackpack());
		TabAPI.getInventoryGroup().getSide(Side.CLIENT).add(TabAPI.getBackpackTab());

		TabAPI.INSTANCE.register(TabAPI.getInventoryGroup());
		TabAPI.getInventoryGroup().getSide(Side.CLIENT).add(UNIVERSE_HOPPER_TAB);

		TabGroupHandler socialTab = new TabGroupHandler();
		socialTab.getSide(Side.CLIENT).add(new TabChat());
		socialTab.getSide(Side.CLIENT).add(GROUP_TAB);
		socialTab.getSide(Side.CLIENT).add(new TabNotifications());
		TabAPI.INSTANCE.register(socialTab);
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
