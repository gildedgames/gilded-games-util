package com.gildedgames.util.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.client.GuiIngame;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.menu.MenuCore;
import com.gildedgames.util.menu.client.IMenu;
import com.gildedgames.util.menu.client.MenuClientEvents;
import com.gildedgames.util.menu.client.util.MenuMinecraft;
import com.gildedgames.util.tab.client.TabClientEvents;
import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.tab.common.util.TabGroupHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends ServerProxy
{

	private final Minecraft mc = Minecraft.getMinecraft();

	public static final IMenu MINECRAFT_MENU = new MenuMinecraft();

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

		MenuClientEvents menuClientEvents = new MenuClientEvents();
		UtilCore.registerEventHandler(menuClientEvents);
		MenuCore.INSTANCE.registerMenu(MINECRAFT_MENU);

		TabClientEvents clientEvents = new TabClientEvents();
		UtilCore.registerEventHandler(clientEvents);

		TabAPI.setBackpackTab(new TabBackpack());
		TabAPI.getInventoryGroup().getSide(Side.CLIENT).add(TabAPI.getBackpackTab());

		TabAPI.INSTANCE.register(TabAPI.getInventoryGroup());
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		UtilCore.registerEventHandler(new GuiIngame(Minecraft.getMinecraft()));
	}

	@Override
	public void addScheduledTask(Runnable runnable)
	{
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}

}
