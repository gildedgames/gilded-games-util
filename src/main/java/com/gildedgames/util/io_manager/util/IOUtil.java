package com.gildedgames.util.io_manager.util;

import net.minecraft.server.MinecraftServer;

public class IOUtil
{
	public static String getMinecraftDirectory()
	{
		return MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", "");
	}
}
