package com.gildedgames.util.worldhook.common.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IWorld extends IBlockAccess
{
	
	boolean setBlockState(BlockPos pos, IBlockState state);
	
	boolean setBlockState(BlockPos pos, IBlockState newState, int flags);
	
	boolean setBlockToAir(BlockPos pos);
	
	boolean destroyBlock(BlockPos pos, boolean dropBlock);
	
	int getDimensionID();
	
}
