package com.gildedgames.util;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

/**
 * This class contains simple static methods that are useful in Minecraft
 * @author Emile
 *
 */
public class GGHelper 
{
	public static boolean contains(StructureBoundingBox boundingBox, BlockPos pos)
	{
		return pos.getX() >= boundingBox.minX && pos.getX() <= boundingBox.maxX && pos.getZ() >= boundingBox.minZ && pos.getZ() <= boundingBox.maxZ && pos.getY() >= boundingBox.minY && pos.getY() <= boundingBox.maxY;
	}
	
	/**
	 * Gets the IBlockState associated with an ItemStack. Returns null if the ItemStack doesn't have an ItemBlock
	 */
	public static IBlockState getBlockState(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemBlock)
		{
			return ((ItemBlock) stack.getItem()).block.getStateFromMeta(stack.getItemDamage());
		}
		return null;
	}

	public static IBlockState getAirState()
	{
		return Blocks.air.getDefaultState();
	}

	public static ItemStack getItemStack(IBlockState state, int amount)
	{
		final Block block = state.getBlock();
		return new ItemStack(block, amount, block.getMetaFromState(state));
	}

	public static boolean isAir(IBlockState state)
	{
		return state.getBlock().getMaterial() == Material.air;
	}
}
