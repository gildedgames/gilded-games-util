package com.gildedgames.util.world.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
	public Block getBlock(int x, int y, int z)
	{
		return this.world.getBlock(x, y, z);
	}

	@Override
	public boolean isAirBlock(int x, int y, int z)
	{
		return this.world.isAirBlock(x, y, z);
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default)
	{
		return this.world.isSideSolid(x, y, z, side, _default);
	}

	@Override
	public boolean setBlock(int x, int y, int z, Block block, int meta)
	{
		return this.world.setBlock(x, y, z, block, meta, 3);
	}

	@Override
	public boolean setBlock(int x, int y, int z, Block block, int meta, int flags)
	{
		return this.world.setBlock(x, y, z, block, meta, flags);
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

	@Override
	public boolean isWrapperFor(int dimId, boolean isRemote)
	{
		return this.world.provider.dimensionId == dimId && this.world.isRemote == isRemote;
	}

	@Override
	public boolean isRemote()
	{
		return this.world.isRemote;
	}

	@Override
	public IBlockAccess getBlockAccess()
	{
		return this.world;
	}

}
