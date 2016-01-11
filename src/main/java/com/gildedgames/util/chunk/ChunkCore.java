package com.gildedgames.util.chunk;

import com.gildedgames.util.core.ICore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class ChunkCore implements ICore
{
	public static ChunkCore INSTANCE = new ChunkCore();

	private final ChunkServices services = new ChunkServices();

	@Override
	public void preInit(FMLPreInitializationEvent event) { }

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this.services);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) { }

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event) { }

	@Override
	public void serverStopping(FMLServerStoppingEvent event) { }

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		// Clean up when the server stops to prevent data from persisting across different world files.
		ChunkCore.locate().clean();
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) { }

	@Override
	public void serverStarted(FMLServerStartedEvent event) { }

	@Override
	public void flushData()
	{
		// Due to the nature of chunk hooks, this will never be implemented.
		// Chunk hooks are saved during Minecraft's regular chunk saving procedure.
	}

	public static ChunkServices locate()
	{
		return ChunkCore.INSTANCE.services;
	}
}
