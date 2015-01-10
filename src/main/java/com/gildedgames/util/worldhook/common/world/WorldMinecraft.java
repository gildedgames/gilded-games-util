package com.gildedgames.util.worldhook.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldMinecraft implements IWorld
{

	private World world;

	public WorldMinecraft(World world)
	{
		this.world = world;
	}

	@Override
	public TileEntity getTileEntity(int x, int y, int z)
	{
		return this.world.getTileEntity(x, y, z);
	}

	@Override
	public Block getBlockState(int x, int y, int z)
	{
		return this.world.getBlock(x, y, z);
	}

	@Override
	public boolean isAirBlock(int x, int y, int z)
	{
		return this.world.isAirBlock(x, y, z);
	}

	@Override
	public boolean setBlockState(int x, int y, int z, Block state)
	{
		return this.world.setBlock(x, y, z, state);
	}

	@Override
	public boolean setBlockState(int x, int y, int z, Block newState, int flags)
	{
		return this.world.setBlock(x, y, z, newState, 0, flags);
	}

	@Override
	public boolean setBlockToAir(int x, int y, int z)
	{
		return this.setBlockToAir(x, y, z);
	}

	@Override
	public boolean destroyBlock(int x, int y, int z, boolean dropBlock)
	{
		return this.destroyBlock(x, y, z, dropBlock);
	}

	@Override
	public int getDimensionID()
	{
		return this.world.provider.dimensionId;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		return this.world.equals(obj);
	}

	@Override
	public Random getRandom()
	{
		return this.world.rand;
	}

	@Override
	public void setTileEntity(int x, int y, int z, TileEntity tileEntity)
	{
		this.world.setTileEntity(x, y, z, tileEntity);
	}

}
