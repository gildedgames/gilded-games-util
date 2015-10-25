package com.gildedgames.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.gildedgames.util.core.UtilCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
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

	public static boolean contains(StructureBoundingBox boundingBox, int x, int y, int z)
	{
		return x >= boundingBox.minX && x <= boundingBox.maxX && z >= boundingBox.minZ && z <= boundingBox.maxZ && y >= boundingBox.minY && y <= boundingBox.maxY;
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

	public static boolean isSolid(IBlockState state, World world, BlockPos pos)
	{
		return !isAir(state) && state.getBlock().isBlockSolid(world, pos, EnumFacing.DOWN) && state.getBlock().getMaterial().isOpaque();
	}

	public static BlockPos getBlockPos(NBTTagCompound tag, String key)
	{
		final NBTTagCompound newTag = tag.getCompoundTag(key);
		if (newTag == null || newTag.hasNoTags())
		{
			return null;
		}
		return new BlockPos(newTag.getInteger("x"), newTag.getInteger("y"), newTag.getInteger("z"));
	}

	public static void setBlockPos(NBTTagCompound tag, BlockPos pos, String key)
	{
		if (pos == null)
		{
			return;
		}
		final NBTTagCompound newTag = new NBTTagCompound();
		newTag.setInteger("x", pos.getX());
		newTag.setInteger("y", pos.getY());
		newTag.setInteger("z", pos.getZ());
		tag.setTag(key, newTag);
	}

	public static Iterable<BlockPos> getInBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		return BlockPos.getAllInBoxMutable(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ));
	}

	public static Iterable<BlockPos> getInBox(AxisAlignedBB boundingBox)
	{
		return getInBox((int) boundingBox.minX, (int) boundingBox.minY, (int) boundingBox.minZ, (int) boundingBox.maxX, (int) boundingBox.maxY, (int) boundingBox.maxZ);
	}

	public static NBTTagCompound readNBTFromFile(String fileName)
	{
		return readNBTFromFile(new File(UtilCore.getWorldDirectory(), fileName));
	}

	public static NBTTagCompound readNBTFromFile(File file)
	{
		try
		{
			if (!file.exists())
			{
				return null;
			}
			FileInputStream inputStream = new FileInputStream(file);
			return CompressedStreamTools.readCompressed(inputStream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void writeNBTToFile(NBTTagCompound tag, String fileName)
	{
		writeNBTToFile(tag, new File(UtilCore.getWorldDirectory(), fileName));
	}

	public static void writeNBTToFile(NBTTagCompound tag, File file)
	{
		file.mkdirs();
		File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
		try
		{
			CompressedStreamTools.writeCompressed(tag, new FileOutputStream(tmpFile));
			if (file.exists())
			{
				file.delete();
			}
			tmpFile.renameTo(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
