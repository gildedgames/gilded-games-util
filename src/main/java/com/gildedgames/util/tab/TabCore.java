package com.gildedgames.util.tab;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.common.networking.packet.PacketOpenTab;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

public class TabCore implements ICore
{
	
	public static final TabCore INSTANCE = new TabCore();
	
	private final SidedObject<TabServices> serviceLocator = new SidedObject<TabServices>(new TabServices(), new TabServices());

	public static TabServices locate()
	{
		return TabCore.INSTANCE.serviceLocator.instance();
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilCore.NETWORK.registerMessage(PacketOpenTab.class, PacketOpenTab.class, Side.CLIENT);
		UtilCore.NETWORK.registerMessage(PacketOpenTab.class, PacketOpenTab.class, Side.SERVER);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		
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
