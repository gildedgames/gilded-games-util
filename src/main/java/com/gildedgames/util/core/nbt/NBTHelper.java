package com.gildedgames.util.core.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

import java.io.*;

public class NBTHelper
{

	public static NBTTagCompound readInputNBT(DataInputStream input) throws IOException
	{
		if (input.readBoolean())
		{
			return CompressedStreamTools.read(input);
		}
		return null;
	}

	public static void writeOutputNBT(NBTTagCompound tag, DataOutputStream output) throws IOException
	{
		if (tag == null)
		{
			output.writeBoolean(false);
		}
		else
		{
			output.writeBoolean(true);
			CompressedStreamTools.write(tag, output);
		}
	}

	public static void save(String fileName, NBTTagCompound tag)
	{
		final File file = new File(getWorldFolderPath(), fileName);
		save(file, tag);
	}

	public static NBTTagCompound load(String fileName)
	{
		final File file = new File(getWorldFolderPath(), fileName);
		if (!file.exists())
		{
			return new NBTTagCompound();
		}
		return load(file);
	}

	private static String getWorldFolderPath()
	{
		if (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers != null && MinecraftServer.getServer().worldServers[0] != null)
		{
			return MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", "");
		}
		return null;
	}

	public static void save(File file, NBTTagCompound tag)
	{
		try
		{
			final File directoryFile = file.getParentFile();
			directoryFile.mkdirs();
			file.createNewFile();
			final FileOutputStream outputStream = new FileOutputStream(file);

			CompressedStreamTools.writeCompressed(tag, outputStream);

			outputStream.close();
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public static NBTTagCompound load(File file)
	{
		try
		{
			final FileInputStream inputStream = new FileInputStream(file);

			final NBTTagCompound tag = CompressedStreamTools.readCompressed(inputStream);

			inputStream.close();

			return tag;
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] fromTag(NBTTagCompound tag)
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(byteStream);

		try
		{
			NBTHelper.writeOutputNBT(tag, stream);
			byte[] bytez = byteStream.toByteArray();
			stream.close();
			return bytez;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return new byte[0];
	}
	
	public static NBTTagList encodeStackList(ItemStack stackList[])
	{
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < stackList.length; ++i)
		{
			if (stackList[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				stackList[i].writeToNBT(nbttagcompound);
				tagList.appendTag(nbttagcompound);
			}
		}

		return tagList;
	}

	public static ItemStack[] decodeStackList(NBTTagList tagList)
	{
		ItemStack stackList[] = new ItemStack[8];

		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

			if (itemstack != null)
			{
				if (j >= 0 && j < stackList.length)
				{
					stackList[j] = itemstack;
				}
			}
		}

		return stackList;
	}
	
}
