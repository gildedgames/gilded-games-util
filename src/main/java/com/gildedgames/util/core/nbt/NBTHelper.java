package com.gildedgames.util.core.nbt;

import com.gildedgames.util.core.util.BlockPosDimension;
import com.gildedgames.util.io.ClassSerializer;
import com.gildedgames.util.io_manager.io.NBT;
import com.google.common.collect.AbstractIterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;

public class NBTHelper
{
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
		if (tag.getBoolean(key + "_null"))
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
