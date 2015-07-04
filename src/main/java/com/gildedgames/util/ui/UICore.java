package com.gildedgames.util.ui;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.group.common.IGroup;
import com.gildedgames.util.group.common.IGroupPool;
import com.gildedgames.util.group.common.network.IGroupController;
import com.gildedgames.util.group.common.network.IGroupPoolController;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.player.PlayerCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

public class UiCore implements ICore
{

	public final static UiCore INSTANCE = new UiCore();

	private final SidedObject<UiServices> serviceLocator = new SidedObject<UiServices>(new UiServices(Side.CLIENT), new UiServices(Side.SERVER));

	public static UiServices locate()
	{
		return UiCore.INSTANCE.serviceLocator.instance();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		
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
