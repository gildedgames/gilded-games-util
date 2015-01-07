package com.gildedgames.util.worldhook.common;

import java.io.File;
import java.io.IOException;

import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.gildedgames.util.io_manager.util.nbt.NBTFactory;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.worldhook.WorldHookCore;

public class WorldEventHandler
{

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			for (IWorldPool pool : WorldHookCore.locate().getPools())
			{
				IWorldHook world = pool.get(event.world);

				world.onUpdate();
			}
		}
	}
	
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event)
	{
		World world = event.world;

		ISaveHandler saveHandler = world.getSaveHandler();
		File worldFile = saveHandler.getWorldDirectory();
		
		File location = new File(worldFile, "hook\\world\\");
		
		for (IWorldPool pool : WorldHookCore.locate().getPools())
		{
			try
			{
				location = new File(location, "DIM" + world.provider.getDimensionId() +".dat");
				
				WorldHookCore.locate().getIO().readFile(location, new NBTFile(location, pool, IWorldPool.class), new NBTFactory());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			IWorldHook worldHook = pool.get(event.world);

			worldHook.onLoad();
		}
	}
	
	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event)
	{
		World world = event.world;

		ISaveHandler saveHandler = world.getSaveHandler();
		File worldFile = saveHandler.getWorldDirectory();
		
		File location = new File(worldFile, "hook\\world\\");
		
		for (IWorldPool pool : WorldHookCore.locate().getPools())
		{
			if (pool != null)
			{
				try
				{
					location = new File(location, "DIM" + world.provider.getDimensionId() + ".dat");

					WorldHookCore.locate().getIO().writeFile(location, new NBTFile(location, pool, IWorldPool.class), new NBTFactory());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				IWorldHook worldHook = pool.get(event.world);
				
				worldHook.onUnload();
				
				pool.clear();
			}
			else
			{
				/** TO-DO: error log here, manager should never be null **/
			}
		}
	}
	
	@SubscribeEvent
	public void onSave(WorldEvent.Save event)
	{
		World world = event.world;

		ISaveHandler saveHandler = world.getSaveHandler();
		File worldFile = saveHandler.getWorldDirectory();
		
		for (IWorldPool pool : WorldHookCore.locate().getPools())
		{
			IWorldHook worldHook = pool.get(world);

			worldHook.onSave();
		}
	}
	
}
