package com.gildedgames.util.instances;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;

public class InstanceCore implements ICore
{
	private SidedObject<InstanceServices> services = new SidedObject<InstanceServices>(new InstanceServices(Side.CLIENT), new InstanceServices(Side.SERVER));

	public static InstanceCore INST = new InstanceCore();

	public InstanceServices locate()
	{
		return this.services.instance();
	}

	public PlayerInstances getPlayer(EntityPlayer player)
	{
		return this.locate().getPlayers().get(player);
	}

	public PlayerInstances getPlayer(UUID uuid)
	{
		return this.locate().getPlayers().get(uuid);
	}

	/**
	 * Call in Init()
	 * @param dimension
	 * @param providerClass
	 * @param factory
	 * @return
	 */
	public <T extends Instance> InstanceHandler<T> createInstanceHandler(int dimension, Class<? extends WorldProvider> providerClass, InstanceFactory<T> factory)
	{
		DimensionManager.registerProviderType(dimension, providerClass, true);//TODO: Maybe can be false?
		DimensionManager.registerDimension(dimension, dimension);

		InstanceHandler<T> handler = new InstanceHandler<T>(dimension, factory);
		this.services.server().addHandler(handler);
		this.services.client().addHandler(handler);
		return handler;
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
