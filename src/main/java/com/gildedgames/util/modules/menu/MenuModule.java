package com.gildedgames.util.modules.menu;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.menu.api.MenuServices;
import com.gildedgames.util.modules.menu.client.IMenu;
import com.gildedgames.util.modules.menu.client.MenuClientEvents;
import com.gildedgames.util.modules.menu.client.util.MenuMinecraft;
import com.gildedgames.util.modules.menu.impl.MenuServicesImpl;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MenuModule extends Module
{
	public static final MenuModule INSTANCE = new MenuModule();

	public static final IMenu MINECRAFT_MENU = new MenuMinecraft();

	private final SidedObject<MenuServicesImpl> serviceLocator = new SidedObject<>(new MenuServicesImpl(), new MenuServicesImpl());

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilModule.registerEventHandler(new MenuClientEvents());

		MenuModule.INSTANCE.registerMenu(MINECRAFT_MENU);
	}

	public static MenuServices locate()
	{
		return INSTANCE.serviceLocator.instance();
	}

	public void registerMenu(IMenu menu)
	{
		this.serviceLocator.client().registerMenu(menu);
	}

}
