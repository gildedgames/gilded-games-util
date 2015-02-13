package com.gildedgames.util.threadedlighting;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class ThreadedLighting extends DummyModContainer
{

	private static boolean debugMode = true;

	private static final Logger logger = LogManager.getLogger("THREADED_LIGHTING");

	public static final String VERSION = "1.7.10-1.0";

	public static final String MOD_ID = "threaded-lighting";

	public ThreadedLighting()
	{
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();

		meta.modId = "mod_ThreadedLighting";
		meta.name = "Threaded Lighting";
		meta.version = "1.7.10-1.0";
		meta.credits = "cafaxo";
		meta.authorList = Arrays.asList("Gilded Games");
		meta.description = "A core mod which implements a threaded environment for world lighting updates. This fixes a lot of core issues associated with lighting updates.";
		meta.url = "www.gilded-games.com/threaded-lighting/";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	public static void print(String line)
	{
		if (debugMode)
		{
			logger.info(line);
		}
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		return true;
	}
}
