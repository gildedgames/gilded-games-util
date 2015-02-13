package com.gildedgames.util.core.nbt.util;

import java.io.ByteArrayOutputStream;
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
}
