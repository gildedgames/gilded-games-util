package com.gildedgames.util.worldhook.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public interface IWorld
{

	Block getBlockState(int x, int y, int z);

	boolean setBlockState(int x, int y, int z, Block state);

	boolean setBlockState(int x, int y, int z, Block newState, int flags);

	boolean setBlockToAir(int x, int y, int z);

	boolean destroyBlock(int x, int y, int z, boolean dropBlock);

	int getDimensionID();

	Random getRandom();

	void setTileEntity(int x, int y, int z, TileEntity tileEntity);
	
	TileEntity getTileEntity(int x, int y, int z);
	
	boolean isAirBlock(int x, int y, int z);

}
