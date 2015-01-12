package com.gildedgames.util.worldhook.common.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldMinecraft implements IWorld
{

	private World world;

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
		return this.setBlockToAir(pos);
	}

	@Override
	public boolean destroyBlock(BlockPos pos, boolean dropBlock)
	{
		return this.destroyBlock(pos, dropBlock);
	}

	@Override
	public int getDimensionID()
	{
		return this.world.provider.getDimensionId();
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
	public void setTileEntity(BlockPos pos, TileEntity tileEntity)
	{
		this.world.setTileEntity(pos, tileEntity);
	}

	@Override
	public boolean isWrapperFor(int dimId, boolean isRemote)
	{
		return this.world.provider.getDimensionId() == dimId && this.world.isRemote == isRemote;
	}

	@Override
	public boolean isRemote()
	{
		return this.world.isRemote;
	}

}
