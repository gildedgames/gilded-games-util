package com.gildedgames.util.io_manager.util;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class IOUtil
{

	public static <I, O> void setIOList(String key, List<? extends IO<I, O>> list, IOFactory<I, O> factory, O output)
	{
		IOBridge outputBridge = factory.createOutputBridge(output);

		outputBridge.setInteger(key + "listSize", list.size());

		for (int count = 0; count < list.size(); count++)
		{
			IO<I, O> obj = list.get(count);

			IOCore.io().set(key + "IO" + count, output, factory, obj);
		}
	}

	public static <I, O, T extends IO<I, O>> List<T> getIOList(String key, IOFactory<I, O> factory, I input)
	{
		IOBridge inputBridge = factory.createInputBridge(input);

		int listSize = inputBridge.getInteger(key + "listSize");

		List<T> list = new ArrayList<T>(listSize);

		for (int count = 0; count < listSize; count++)
		{
			T obj = IOCore.io().get(key + "IO" + count, input, factory);

			list.add(obj);
		}

		return list;
	}

	public static <I, O> void setIOMap(String key, Map<? extends IO<I, O>, ? extends IO<I, O>> map, IOFactory<I, O> factory, O output)
	{
		IOBridge outputBridge = factory.createOutputBridge(output);
		outputBridge.setInteger(key + "mapSize", map.size());

		int count = 0;

		for (Entry<? extends IO<I, O>, ? extends IO<I, O>> entry : map.entrySet())
		{
			IO<I, O> keyObj = entry.getKey();
			IO<I, O> valueObj = entry.getValue();

			IOCore.io().set(key + "IOkey" + count, output, factory, keyObj);
			IOCore.io().set(key + "IOvalue" + count, output, factory, valueObj);

			count++;
		}
	}

	public static <I, O> Map<? extends IO<I, O>, ? extends IO<I, O>> getIOMap(String key, IOFactory<I, O> factory, I input)
	{
		IOBridge inputBridge = factory.createInputBridge(input);
		int size = inputBridge.getInteger(key + "mapSize");

		Map<IO<I, O>, IO<I, O>> map = new HashMap<IO<I, O>, IO<I, O>>(size);

		for (int count = 0; count < size; count++)
		{
			IO<I, O> keyObj = IOCore.io().get(key + "IOkey" + count, input, factory);
			IO<I, O> valueObj = IOCore.io().get(key + "IOvalue" + count, input, factory);

			map.put(keyObj, valueObj);
		}

		return map;
	}

	public static File[] getFoldersInDirectory(File directory)
	{
		if (!directory.exists())
		{
			directory.mkdirs();
		}

		final File[] files = directory.listFiles();
		final ArrayList<File> fileList = new ArrayList<File>();

		if (files != null)
		{
			for (final File file : files)
			{
				if (file.isDirectory())
				{
					fileList.add(file);
				}
			}
		}

		return fileList.toArray(new File[fileList.size()]);
	}

	public static List<File> getFilesWithExtension(File directory, List<String> extensions)
	{
		final List<File> files = new ArrayList<File>();
		for (final String string : extensions)
		{
			final File[] fileArray = getFilesWithExtension(directory, string);

			if (fileArray != null)
			{
				files.addAll(Arrays.asList(fileArray));
			}
		}
		return files;
	}

	public static File[] getFilesWithExtension(File directory, String extension)
	{
		if (!directory.exists())
		{
			directory.mkdirs();
		}

		return directory.listFiles(new FilenameFilterExtension(extension));
	}

	public static void writeUUID(UUID uuid, ByteBuf buf)
	{
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

	public static UUID readUUID(ByteBuf buf)
	{
		return new UUID(buf.readLong(), buf.readLong());
	}

	public static void setUUID(IPlayerProfile profile, NBTTagCompound tag, String name)
	{
		setUUID(profile.getUUID(), tag, name);
	}

	public static void setUUID(UUID uuid, NBTTagCompound tag, String key)
	{
		tag.setLong(key + "most", uuid.getMostSignificantBits());
		tag.setLong(key + "least", uuid.getLeastSignificantBits());
	}

	public static UUID getUUID(NBTTagCompound tag, String name)
	{
		return new UUID(tag.getLong(name + "most"), tag.getLong("least"));
	}

	private static class FilenameFilterExtension implements FilenameFilter
	{

		public final String extension;

		public FilenameFilterExtension(String extension)
		{
			super();
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith("." + this.extension);
		}

	}

}
