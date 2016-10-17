package com.gildedgames.util.core.nbt;

import com.gildedgames.util.core.util.BlockPosDimension;
import com.gildedgames.util.io.ClassSerializer;
import com.gildedgames.util.io_manager.io.NBT;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;

public class NBTHelper
{

	public static <T extends NBT> void fullySerializeList(String key, List<T> nbt, NBTTagCompound tag)
	{
		tag.setBoolean(key + "_null", nbt == null);

		if (nbt == null)
		{
			return;
		}

		NBTTagCompound list_data = new NBTTagCompound();

		list_data.setInteger("size", nbt.size());
		int i = 0;

		for (T obj : nbt)
		{
			tag.setBoolean("data_" + i + "_null", obj == null);

			if (obj == null)
			{
				i++;
				continue;
			}

			ClassSerializer.writeSerialNumber("data_" + i + "_srl", nbt, tag);

			NBTTagCompound data = new NBTTagCompound();
			obj.write(data);

			list_data.setTag("data_" + i, data);

			i++;
		}

		tag.setTag(key + "_list_data", list_data);
	}

	@SuppressWarnings("unchecked")
	public static <T extends NBT> List<T> fullyDeserializeList(String key, NBTTagCompound tag)
	{
		if (!tag.hasKey(key + "_null") || tag.getBoolean(key + "_null"))
		{
			return null;
		}

		NBTTagCompound list_data = tag.getCompoundTag(key + "_list_data");

		List<T> list = Lists.newArrayList();

		int size = list_data.getInteger("size");

		for (int i = 0; i < size; i++)
		{
			if (!list_data.hasKey("data_" + i + "_null") || list_data.getBoolean("data_" + i + "_null"))
			{
				continue;
			}

			T nbt = (T) ClassSerializer.instantiate("data_" + i + "_srl", list_data);

			nbt.read(list_data.getCompoundTag("data_" + i));

			list.add(nbt);
		}

		return list;
	}

	public static <T extends NBT> void fullySerialize(String key, T nbt, NBTTagCompound tag)
	{
		tag.setBoolean(key + "_null", nbt == null);

		if (nbt == null)
		{
			return;
		}

		ClassSerializer.writeSerialNumber(key, nbt, tag);

		NBTTagCompound data = new NBTTagCompound();
		nbt.write(data);

		tag.setTag(key + "_data", data);
	}

	@SuppressWarnings("unchecked")
	public static <T extends NBT> T fullyDeserialize(String key, NBTTagCompound tag)
	{
		if (!tag.hasKey(key + "_null") || tag.getBoolean(key + "_null"))
		{
			return null;
		}

		T nbt = (T) ClassSerializer.instantiate(key, tag);

		nbt.read(tag.getCompoundTag(key + "_data"));

		return nbt;
	}

	public static NBTTagList getTagList(NBTTagCompound tag, String key)
	{
		return tag.getTagList(key, 10);
	}

	public static Iterable<NBTTagCompound> getIterator(NBTTagCompound tag, String tagListKey)
	{
		return getIterator(getTagList(tag, tagListKey));
	}

	/**
	 * Get the iterator for a taglist in an NBTTagCompound.
	 * Simply a nice shortcut method.
	 */
	public static Iterable<NBTTagCompound> getIterator(final NBTTagList tagList)
	{
		return new Iterable<NBTTagCompound>()
		{
			@Override
			public Iterator<NBTTagCompound> iterator()
			{
				return new AbstractIterator<NBTTagCompound>()
				{
					private int i = 0;

					@Override
					protected NBTTagCompound computeNext()
					{
						if (this.i >= tagList.tagCount())
						{
							return this.endOfData();
						}

						NBTTagCompound tag = tagList.getCompoundTagAt(this.i);
						this.i++;
						return tag;
					}
				};
			}
		};
	}

	public static BlockPosDimension getBlockPosDimension(NBTTagCompound tag)
	{
		if (tag == null || (tag.hasKey("_null") && tag.getBoolean("_null")))
		{
			return null;
		}

		return new BlockPosDimension(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("d"));
	}

	public static BlockPos readBlockPos(NBTTagCompound tag)
	{
		if (tag == null || (tag.hasKey("_null") && tag.getBoolean("_null")))
		{
			return null;
		}

		return new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
	}

	public static NBTBase serializeBlockPos(BlockPos pos)
	{
		NBTTagCompound tag = new NBTTagCompound();

		if (pos == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());

		return tag;
	}

	public static NBTTagCompound serializeBlockPosDimension(BlockPosDimension pos)
	{
		NBTTagCompound tag = new NBTTagCompound();

		if (pos == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());
		tag.setInteger("d", pos.dimId());

		return tag;
	}
}
