package com.gildedgames.util.core;

import com.gildedgames.util.menu.MenuCore;
import com.gildedgames.util.menu.client.IMenu;
import com.gildedgames.util.menu.client.MenuClientEvents;
import com.gildedgames.util.menu.client.util.MenuMinecraft;
import com.gildedgames.util.tab.client.TabClientEvents;
import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.universe.client.gui.TabUniverseHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

//import com.gildedgames.util.ui.TestTab;

public class ClientProxy extends ServerProxy
{

	private final Minecraft mc = Minecraft.getMinecraft();

	public static final IMenu MINECRAFT_MENU = new MenuMinecraft();

	public static final ITab UNIVERSE_HOPPER_TAB = new TabUniverseHopper();
	
	public static KeyBinding keyBindHopUniverse = new KeyBinding(StatCollector.translateToLocal("keybindings.hopUniverse"), Keyboard.KEY_H, "key.categories.misc");

	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
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
	public void init(FMLInitializationEvent event)
	{
		ClientRegistry.registerKeyBinding(keyBindHopUniverse);
	}

	@Override
	public void addScheduledTask(Runnable runnable)
	{
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}

}
