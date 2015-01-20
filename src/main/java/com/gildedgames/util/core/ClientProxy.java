package com.gildedgames.util.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gildedgames.util.menu.MenuCore;
import com.gildedgames.util.menu.client.IMenu;
import com.gildedgames.util.menu.client.MenuClientEvents;
import com.gildedgames.util.menu.client.util.MenuMinecraft;

public class ClientProxy extends ServerProxy
{

	private Minecraft mc = Minecraft.getMinecraft();

	public static final IMenu MINECRAFT_MENU = new MenuMinecraft();

	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		MenuClientEvents menuClientEvents = new MenuClientEvents();

		MinecraftForge.EVENT_BUS.register(menuClientEvents);
		FMLCommonHandler.instance().bus().register(menuClientEvents);

		MenuCore.INSTANCE.registerMenu(MINECRAFT_MENU);
	}

}
