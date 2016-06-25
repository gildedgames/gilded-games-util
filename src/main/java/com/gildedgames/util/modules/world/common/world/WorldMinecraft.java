package com.gildedgames.util.modules.world.common.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class WorldMinecraft implements IWorld
{

	private final World world;

	public WorldMinecraft(World world)
	{
		this.world = world;
	}

	@Override
	public TileEntity getTileEntity(BlockPos pos)
	{
		return this.world.getTileEntity(pos);
	}

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		return this.world.getBlockState(pos);
	}

	@Override
	public boolean isAirBlock(BlockPos pos)
	{
		return this.world.isAirBlock(pos);
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
	{
		return this.world.isSideSolid(pos, side, _default);
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState state)
	{
		return this.world.setBlockState(pos, state);
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
	{
		return this.world.setBlockState(pos, newState, flags);
	}

	@Override
	public boolean setBlockToAir(BlockPos pos)
	{
		return this.world.setBlockToAir(pos);
	}

	@Override
	public boolean destroyBlock(BlockPos pos, boolean dropBlock)
	{
		return this.world.destroyBlock(pos, dropBlock);
	}

	@Override
	public int getDimensionID()
	{
		return this.world.provider.getDimension();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}

		if (this.world.equals(obj))
		{
			return true;
		}
		if (obj instanceof IWorld)
		{
			IWorld world = (IWorld) obj;
			return world.getDimensionID() == this.getDimensionID();
		}
		return false;
	}

	@Override
	public Random getRandom()
	{
		return this.world.rand;
	}

	@Override
	public void setTileEntity(BlockPos pos, TileEntity tileEntity)
	{
		this.world.setTileEntity(pos, tileEntity);
	}

	@Override
	public boolean isWrapperFor(int dimId, boolean isRemote)
	{
		return this.world.provider.getDimension() == dimId && this.world.isRemote == isRemote;
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
