package com.gildedgames.util.core.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

public class NBTDataHandler
{

	private String path;

	public NBTDataHandler(String mainDirectory)
	{
		this.path = mainDirectory;
		if (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers != null && MinecraftServer.getServer().worldServers[0] != null)
		{
			this.path = MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", "");
		}
	}

	public void save(String fileName, Collection<? extends NBT> objects)
	{
		NBTTagList tagList = new NBTTagList();

		for (NBT object : objects)
		{
			NBTTagCompound tag = new NBTTagCompound();
			object.write(tag);
			tagList.appendTag(tag);
		}

		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("tagList", tagList);

		File file = new File(this.path, fileName);

		try
		{
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);

			CompressedStreamTools.writeCompressed(tag, outputStream);

			outputStream.close();
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}
	}

	public <T extends NBT> ArrayList<T> load(Class<T> clazz, String fileName)
	{
		File file = new File(this.path, fileName);

		try
		{
			FileInputStream inputStream = new FileInputStream(file);

			NBTTagCompound tag = CompressedStreamTools.readCompressed(inputStream);
			NBTTagList tagList = tag.getTagList("tagList", 10);

			ArrayList<T> list = new ArrayList<T>();

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound objectTag = tagList.getCompoundTagAt(i);

				try
				{
					T object = clazz.newInstance();
					object.read(objectTag);
					list.add(object);
				}
				catch (InstantiationException e)
				{
				}
				catch (IllegalAccessException e)
				{
				}
			}

			inputStream.close();

			return list;
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}

		return new ArrayList<T>();
	}

	public void saveMap(String fileName, Map<? extends NBT, ? extends NBT> objects)
	{
		NBTTagList tagList = new NBTTagList();

		for (Entry<? extends NBT, ? extends NBT> entry : objects.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();

			NBTTagCompound tag1 = new NBTTagCompound();
			entry.getKey().write(tag1);
			tag.setTag("key", tag1);

			NBTTagCompound tag2 = new NBTTagCompound();
			entry.getValue().write(tag2);
			tag.setTag("value", tag2);

			tagList.appendTag(tag);
		}

		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("tagList", tagList);

		File file = new File(this.path, fileName);

		try
		{
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);

			CompressedStreamTools.writeCompressed(tag, outputStream);

			outputStream.close();
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}
	}

	public HashMap<NBT, NBT> loadMap(Class<? extends NBT> keyClass, Class<? extends NBT> valueClass, String fileName)
	{
		File file = new File(this.path, fileName);

		try
		{
			FileInputStream inputStream = new FileInputStream(file);

			NBTTagCompound tag = CompressedStreamTools.readCompressed(inputStream);
			NBTTagList tagList = tag.getTagList("tagList", 10);

			HashMap<NBT, NBT> map = new HashMap<NBT, NBT>();

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound objectTag = tagList.getCompoundTagAt(i);

				try
				{
					NBT key = keyClass.newInstance();
					key.read(objectTag.getCompoundTag("key"));

					NBT value = valueClass.newInstance();
					value.read(objectTag.getCompoundTag("value"));

					map.put(key, value);

				}
				catch (InstantiationException e)
				{
				}
				catch (IllegalAccessException e)
				{
				}
			}

			inputStream.close();

			return map;
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}

		return new HashMap<NBT, NBT>();
	}

}
