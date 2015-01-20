package com.gildedgames.util.group;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.group.common.player.GroupMember;

public class GroupCore implements ICore
{

	public static GroupCore INSTANCE = new GroupCore();
	
	private SidedObject<GroupServices> serviceLocator = new SidedObject<GroupServices>(new GroupServices(), new GroupServices());

	public static GroupServices locate()
	{
		return GroupCore.INSTANCE.serviceLocator.instance();
	}

	public static GroupMember getGroupMember(EntityPlayer player)
	{
		return GroupCore.locate().getPlayers().get(player);
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
