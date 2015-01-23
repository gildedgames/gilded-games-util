package com.gildedgames.util.io_manager.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

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
			/*NBTTagList tagList = new NBTTagList();
			tagList.appendTag(tag);
			dataoutputstream.writeByte(tagList.getId());

			if (tagList.getId() != 0)
			{
				dataoutputstream.writeUTF("");
			    tagList.write(dataoutputstream);
			}*/
			//CompressedStreamTools.writeCompressed(tag, output);
		}
	}

	public static void save(String fileName, NBTTagCompound tag)
	{
		try
		{
			final File file = new File(getWorldFolderPath(), fileName);

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

	public static NBTTagCompound load(String fileName)
	{
		final File file = new File(getWorldFolderPath(), fileName);
		if (!file.exists())
		{
			return new NBTTagCompound();
		}

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

}
