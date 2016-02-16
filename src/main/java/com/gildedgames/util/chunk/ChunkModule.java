package com.gildedgames.util.chunk;

import com.gildedgames.util.chunk.api.ChunkServices;
import com.gildedgames.util.chunk.impl.ChunkServicesImpl;
import com.gildedgames.util.core.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

public class ChunkModule extends Module
{
	public static ChunkModule INSTANCE = new ChunkModule();

	private final ChunkServicesImpl services = new ChunkServicesImpl();

	@Override
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this.services);
	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		// Clean up when the server stops to prevent data from persisting across different world files.
		ChunkModule.impl().clean();
	}

	@Override
	public void flushData()
	{
		// Due to the nature of chunk hooks, this will never be implemented.
		// Chunk hooks are saved during Minecraft's regular chunk saving procedure.
	}

	public static ChunkServices locate()
	{
		return ChunkModule.INSTANCE.services;
	}

	private static ChunkServicesImpl impl()
	{
		return ChunkModule.INSTANCE.services;
	}
}
