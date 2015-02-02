package com.gildedgames.util.testutil.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import com.gildedgames.util.world.common.world.IWorld;

public class TestWorld implements IWorld
{
	int dimId;

	Map<BlockPos, IBlockState> blocks = new HashMap<BlockPos, IBlockState>();

	Map<BlockPos, TileEntity> tileEntities = new HashMap<BlockPos, TileEntity>();

	Random random = new Random();

	public TestWorld(int dimId)
	{
		this.dimId = dimId;
	}

	@Override
	public IBlockAccess getBlockAccess()
	{
		return null;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		return this.blocks.get(pos);
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState state)
	{
		return this.setBlockState(pos, state, 1);
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
	{
		this.blocks.put(pos, newState);
		return true;
	}

	@Override
	public boolean isAirBlock(BlockPos pos)
	{
		return !this.blocks.containsKey(pos);
	}

	@Override
	public boolean setBlockToAir(BlockPos pos)
	{
		this.blocks.remove(pos);
		return true;
	}

	@Override
	public boolean destroyBlock(BlockPos pos, boolean dropBlock)
	{
		return this.setBlockToAir(pos);
	}

	@Override
	public int getDimensionID()
	{
		return this.dimId;
	}

	@Override
	public Random getRandom()
	{
		return this.random;
	}

	@Override
	public TileEntity getTileEntity(BlockPos pos)
	{
		return null;
	}

	@Override
	public void setTileEntity(BlockPos pos, TileEntity tileEntity)
	{
	}

	@Override
	public boolean isWrapperFor(int dimId, boolean isRemote)
	{
		return this.dimId == dimId;
	}

	@Override
	public boolean isRemote()
	{
		return false;
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
	{
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof TestWorld)
		{
			return this.dimId == ((TestWorld) obj).dimId;
		}
		return false;
	}
}
