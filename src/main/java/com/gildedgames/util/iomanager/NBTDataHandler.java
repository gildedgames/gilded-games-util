package com.gildedgames.util.iomanager;

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

	public NBTDataHandler()
	{
		if (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers != null && MinecraftServer.getServer().worldServers[0] != null)
		{
			this.path = (MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName())).getAbsolutePath().replace((MinecraftServer.getServer().getFolderName() + ".dat"), "");
		}
	}

	public void save(String fileName, Collection<? extends INBT> objects)
	{
		NBTTagList tagList = new NBTTagList();

		for (INBT object : objects)
		{
			NBTTagCompound tag = new NBTTagCompound();
			object.writeToNBT(tag);
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

	public ArrayList load(Class<? extends INBT> clazz, String fileName)
	{
		File file = new File(this.path, fileName);

		try
		{
			FileInputStream inputStream = new FileInputStream(file);

			NBTTagCompound tag = CompressedStreamTools.readCompressed(inputStream);
			NBTTagList tagList = tag.getTagList("tagList", 10);

			ArrayList list = new ArrayList();

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound objectTag = (NBTTagCompound) tagList.getCompoundTagAt(i);

				try
				{
					INBT object = clazz.newInstance();
					object.readFromNBT(objectTag);
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

		return new ArrayList();
	}

	public void saveMap(String fileName, Map<? extends INBT, ? extends INBT> objects)
	{
		NBTTagList tagList = new NBTTagList();

		for (Entry entry : objects.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();

			NBTTagCompound tag1 = new NBTTagCompound();
			((INBT) entry.getKey()).writeToNBT(tag1);
			tag.setTag("key", tag1);

			NBTTagCompound tag2 = new NBTTagCompound();
			((INBT) entry.getValue()).writeToNBT(tag2);
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

	public HashMap loadMap(Class<? extends INBT> keyClass, Class<? extends INBT> valueClass, String fileName)
	{
		File file = new File(this.path, fileName);

		try
		{
			FileInputStream inputStream = new FileInputStream(file);

			NBTTagCompound tag = CompressedStreamTools.readCompressed(inputStream);
			NBTTagList tagList = tag.getTagList("tagList", 10);

			HashMap map = new HashMap();

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound objectTag = (NBTTagCompound) tagList.getCompoundTagAt(i);

				try
				{
					INBT key = keyClass.newInstance();
					key.readFromNBT(objectTag.getCompoundTag("key"));

					INBT value = valueClass.newInstance();
					value.readFromNBT(objectTag.getCompoundTag("value"));

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

		return new HashMap();
	}

}
