package com.gildedgames.util.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.gildedgames.util.core.UtilCore;

/**
 * This class contains simple static methods that are useful in Minecraft
 * @author Emile
 *
 */
public class GGHelper
{

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
