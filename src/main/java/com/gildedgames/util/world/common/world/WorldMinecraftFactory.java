package com.gildedgames.util.world.common.world;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.gildedgames.util.core.UtilCore;

public class WorldMinecraftFactory implements IWorldFactory<WorldMinecraft>
{

	@Override
	public WorldMinecraft create(int dimId, boolean isRemote)
	{
		if (isRemote)
		{
			World world = Minecraft.getMinecraft().theWorld;
			if (world.provider.getDimensionId() == dimId)
			{
				return new WorldMinecraft(world);
			}
			else
			{
				UtilCore.print("Tried to access world with Dimension id " + dimId + " on the client, but that's not where the player is.");
				return null;
			}
		}
		World world = MinecraftServer.getServer().worldServerForDimension(dimId);
		return new WorldMinecraft(world);
	}

}
