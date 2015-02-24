package com.gildedgames.util.world.common.world;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.gildedgames.util.core.UtilCore;

public class WorldMinecraftFactoryServer implements IWorldFactory<WorldMinecraft>
{

	@Override
	public WorldMinecraft create(int dimId, boolean isRemote)
	{
		World world = MinecraftServer.getServer().worldServerForDimension(dimId);
		
		return new WorldMinecraft(world);
	}

}
