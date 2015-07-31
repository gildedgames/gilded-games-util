package com.gildedgames.util.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.menu.MenuCore;
import com.gildedgames.util.menu.client.IMenu;
import com.gildedgames.util.menu.client.MenuClientEvents;
import com.gildedgames.util.menu.client.util.MenuMinecraft;
import com.gildedgames.util.minecraft.gamemode.GameModeGuiInjector;
import com.gildedgames.util.minecraft.gamemode.GameModeTracker;
import com.gildedgames.util.tab.client.TabClientEvents;
import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.tab.common.util.ITab;
//import com.gildedgames.util.ui.TestTab;
import com.gildedgames.util.universe.client.gui.TabUniverseHopper;

public class ClientProxy extends ServerProxy
{

	private final Minecraft mc = Minecraft.getMinecraft();

	public static final IMenu MINECRAFT_MENU = new MenuMinecraft();

	public static final ITab UNIVERSE_HOPPER_TAB = new TabUniverseHopper();

	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		GameModeGuiInjector gameModeMenu = new GameModeGuiInjector();
		
		UtilCore.registerEventHandler(gameModeMenu);
		
		GameModeTracker gameModeTracker = new GameModeTracker();
		
		UtilCore.registerEventHandler(gameModeTracker);
		
		UtilEvents utilEvents = new UtilEvents();

		UtilCore.registerEventHandler(utilEvents);
		
		UtilCore.registerEventHandler(this.MinecraftTickInfo);

		MenuClientEvents menuClientEvents = new MenuClientEvents();

		UtilCore.registerEventHandler(menuClientEvents);

		MenuCore.INSTANCE.registerMenu(MINECRAFT_MENU);

		TabClientEvents clientEvents = new TabClientEvents();

		UtilCore.registerEventHandler(clientEvents);

		TabAPI.setBackpackTab(new TabBackpack());

		TabAPI.getInventoryGroup().getSide(Side.CLIENT).add(TabAPI.getBackpackTab());

		TabAPI.INSTANCE.register(TabAPI.getInventoryGroup());

		TabAPI.INSTANCE.getInventoryGroup().getSide(Side.CLIENT).add(UNIVERSE_HOPPER_TAB);
	}

	@Override
	public void addScheduledTask(Runnable runnable)
	{
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}

}
