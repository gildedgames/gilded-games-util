package com.gildedgames.util.modules.tab;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.tab.api.TabAPI;
import com.gildedgames.util.modules.tab.common.TabApiImpl;
import com.gildedgames.util.modules.tab.common.networking.packet.PacketOpenTab;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class TabModule extends Module
{
	public static final TabModule INSTANCE = new TabModule();

	private final TabApiImpl api = new TabApiImpl();

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilModule.NETWORK.registerMessage(PacketOpenTab.class, PacketOpenTab.class, Side.CLIENT);
		UtilModule.NETWORK.registerMessage(PacketOpenTab.class, PacketOpenTab.class, Side.SERVER);
	}

	public static TabAPI api()
	{
		return TabModule.INSTANCE.api;
	}
}
