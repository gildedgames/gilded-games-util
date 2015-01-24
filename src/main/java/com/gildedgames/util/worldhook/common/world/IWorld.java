package com.gildedgames.util.worldhook.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Wrapper around an implementation of Minecraft worlds.
 * Recommended access is through WorldHookCore.getWrapper().
 * @author Emile
 *
 */
public interface IWorld
{

	Block getBlockPos(int x, int y, int z);

	boolean setBlock(int x, int y, int z, Block block, int meta);

	boolean setBlock(int x, int y, int z, Block block, int meta, int flags);

	boolean isAirBlock(int x, int y, int z);

	boolean setBlockToAir(int x, int y, int z);

	boolean destroyBlock(int x, int y, int z, boolean dropBlock);

	int getDimensionID();

	Random getRandom();

	TileEntity getTileEntity(int x, int y, int z);

	void setTileEntity(int x, int y, int z, TileEntity tileEntity);

	boolean isWrapperFor(int dimId, boolean isRemote);

	boolean isRemote();

	boolean isSideSolid(int x, int y , int z, ForgeDirection side, boolean _default);

}
