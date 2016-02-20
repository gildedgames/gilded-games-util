package com.gildedgames.util.io_manager.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.gildedgames.util.core.io.ByteBufBridge;
import com.gildedgames.util.core.io.ByteBufFactory;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.factory.IOFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.modules.player.common.player.IPlayerProfile;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class IOUtil
{

	private final static ByteBufFactory bufFactory = new ByteBufFactory();

	public static <I, O> void setCollection(String key, Collection<? extends IO<IOBridge, IOBridge>> collection, IOBridge output)
	{
		output.setInteger(key + "collectionSize", collection.size());

		int count = 0;

		for (IO<IOBridge, IOBridge> obj : collection)
		{
			output.setIO(key + "IO" + count, obj);

			count++;
		}
	}

	public static <T extends IO<IOBridge, IOBridge>> Collection<T> getCollection(String key, IOBridge input)
	{
		int listSize = input.getInteger(key + "collectionSize");

		List<T> list = new ArrayList<>(listSize);

		for (int count = 0; count < listSize; count++)
		{
			T obj = input.getIO(key + "IO" + count);

			list.add(obj);
		}

		return list;
	}

	public static <I, O> void setArray(String key, IO<IOBridge, IOBridge>[] array, IOBridge output)
	{
		output.setInteger(key + "arraySize", array.length);

		int count = 0;

		for (IO<IOBridge, IOBridge> obj : array)
		{
			output.setIO(key + "IO" + count, obj);

			count++;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends IO<IOBridge, IOBridge>> T[] getArray(String key, IOBridge input)
	{
		int listSize = input.getInteger(key + "arraySize");

		List<T> list = new ArrayList<>(listSize);

		for (int count = 0; count < listSize; count++)
		{
			T obj = input.getIO(key + "IO" + count);

			list.add(obj);
		}

		return (T[]) list.toArray();
	}

	public static <I, O> void setCollection(String key, Collection<? extends IO<I, O>> collection, IOFactory<I, O> factory, O output)
	{
		IOBridge outputBridge = factory.createOutputBridge(output);

		outputBridge.setInteger(key + "collectionSize", collection.size());

		int count = 0;

		for (IO<I, O> obj : collection)
		{
			IOCore.io().set(key + "IO" + count, output, factory, obj);

			count++;
		}
	}

	public static <I, O, T extends IO<I, O>> Collection<T> getCollection(String key, IOFactory<I, O> factory, I input)
	{
		IOBridge inputBridge = factory.createInputBridge(input);

		int listSize = inputBridge.getInteger(key + "collectionSize");

		List<T> list = new ArrayList<>(listSize);

		for (int count = 0; count < listSize; count++)
		{
			T obj = IOCore.io().get(key + "IO" + count, input, factory);

			list.add(obj);
		}

		return list;
	}

	public static void setIOList(String key, List<? extends IO<IOBridge, IOBridge>> list, IOBridge output)
	{
		output.setInteger(key + "listSize", list.size());

		for (int count = 0; count < list.size(); count++)
		{
			output.setIO(key + "IO" + count, list.get(count));
		}
	}

	public static <T extends IO<IOBridge, IOBridge>> List<T> getIOList(String key, IOBridge input)
	{
		int listSize = input.getInteger(key + "listSize");

		List<T> list = new ArrayList<>(listSize);

		for (int count = 0; count < listSize; count++)
		{
			T obj = input.getIO(key + "IO" + count);

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

		Map<IO<I, O>, IO<I, O>> map = new HashMap<>(size);

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
		final ArrayList<File> fileList = new ArrayList<>();

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
		final List<File> files = new ArrayList<>();
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

	public static void setUUID(UUID uuid, IOBridge bridge, String key)
	{
		bridge.setLong(key + "most", uuid.getMostSignificantBits());
		bridge.setLong(key + "least", uuid.getLeastSignificantBits());
	}
	
	public static void setUUID(IPlayerProfile profile, IOBridge bridge, String name)
	{
		setUUID(profile.getUUID(), bridge, name);
	}

	public static UUID getUUID(NBTTagCompound tag, String name)
	{
		return new UUID(tag.getLong(name + "most"), tag.getLong(name + "least"));
	}

	public static UUID getUUID(IOBridge tag, String name)
	{
		return new UUID(tag.getLong(name + "most"), tag.getLong(name + "least"));
	}

	public static <T extends IO<ByteBuf, ByteBuf>> T readIO(ByteBuf buf)
	{
		if (buf.readBoolean())
		{
			return IOCore.io().get("", buf, bufFactory);
		}
		return null;
	}

	public static void writeIO(ByteBuf buf, IO<ByteBuf, ByteBuf> io)
	{
		buf.writeBoolean(io != null);
		if (io != null)
		{
			IOCore.io().set("", buf, bufFactory, io);
		}
	}

	public static void writeIOList(List<? extends IO<IOBridge, IOBridge>> list, ByteBuf buf)
	{
		IOUtil.setCollection("", list, ByteBufBridge.factory, new ByteBufBridge(buf));
	}

	@SuppressWarnings("unchecked")
	public static <T extends IO<IOBridge, IOBridge>> List<T> readIOList(ByteBuf buf)
	{
		return (List<T>) IOUtil.getCollection("", ByteBufBridge.factory, new ByteBufBridge(buf));
	}

	public static Group getGroup(IOBridge bridge, String key)
	{
		return GroupModule.locate().getPoolFromID(bridge.getString(key + "p")).get(getUUID(bridge, key + "u"));
	}

	public static void setGroup(IOBridge bridge, String key, Group group)
	{
		bridge.setString(key + "p", group.getParentPool().getID());
		setUUID(group.getUUID(), bridge, key + "u");
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
