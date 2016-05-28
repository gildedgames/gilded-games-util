package com.gildedgames.util.core.nbt;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import com.gildedgames.util.io.ClassSerializer;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;

public class NBTHelper
{
	
	public static <T extends NBT> void fullySerializeCollection(String key, Collection<T> collection, NBTTagCompound tag)
	{
		NBTTagList list = new NBTTagList();

		int count = 0;

		for (NBT obj : collection)
		{
			NBTTagCompound appendedTag = new NBTTagCompound();
			
			NBTHelper.fullySerialize("data", obj, appendedTag);

			list.appendTag(appendedTag);
			
			count++;
		}
		
		tag.setTag(key, list);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends NBT> Collection<T> fullyDeserializeCollection(String key, NBTTagCompound tag)
	{
		List<T> collection = Lists.newArrayList();
		
		NBTTagList list = tag.getTagList(key, 10);

		if (list != null)
		{
			for (int i = 0; i < list.tagCount(); i++)
			{
				NBTTagCompound compound = list.getCompoundTagAt(i);
				
				collection.add((T) NBTHelper.fullyDeserialize("data", compound));
			}
		}
		
		return collection;
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
		if (tag.getBoolean(key + "_null"))
		{
			return (T)null;
		}
		
		T nbt = (T) ClassSerializer.instantiate(key, tag);
		
		nbt.read(tag.getCompoundTag(key + "_data"));
		
		return nbt;
	}

	public static <T extends NBT> T readInputObject(ByteBuf buf)
	{
		NBTTagCompound tag = readInputNBT(buf);
		return IOCore.io().get("a", tag, new NBTFactory());
	}

	public static NBTTagCompound readInputNBT(ByteBuf buf)
	{
		int size = buf.readInt();
		byte[] array = new byte[size];
		buf.readBytes(array);
		ByteArrayInputStream byteArray = new ByteArrayInputStream(array);
		DataInputStream dataInput = new DataInputStream(byteArray);
		try
		{
			NBTTagCompound tag = readInputNBT(dataInput);
			dataInput.close();
			return tag;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return new NBTTagCompound();
	}

	public static NBTTagCompound readInputNBT(DataInputStream input) throws IOException
	{
		if (input.readBoolean())
		{
			return CompressedStreamTools.read(input);
		}
		return null;
	}

	public static void setIOList(String key, List<? extends IO<IOBridge, IOBridge>> list, NBTTagCompound tag)
	{
		IOUtil.setCollection(key, list, NBTBridge.factory, new NBTBridge(tag));
	}

	@SuppressWarnings("unchecked")
	public static <T extends IO<IOBridge, IOBridge>> List<T> getIOList(String key, NBTTagCompound tag)
	{
		return (List<T>) IOUtil.getCollection(key, NBTBridge.factory, new NBTBridge(tag));
	}

	public static <T extends NBT> void writeOutputObject(T object, ByteBuf byteBuf)
	{
		NBTTagCompound tag = new NBTTagCompound();
		IOCore.io().set("a", tag, new NBTFactory(), object);
		NBTHelper.writeOutputNBT(tag, byteBuf);
	}

	public static void writeOutputNBT(NBTTagCompound tag, ByteBuf byteBuf)
	{
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(byteArray);
		try
		{
			writeOutputNBT(tag, dataOutput);
			byteBuf.writeBytes(byteArray.toByteArray());
			dataOutput.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

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
		ItemStack stackList[] = new ItemStack[tagList.tagCount()];

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

	public static void setEnum(String key, NBTTagCompound tag, Enum<?> e)
	{
		tag.setString(key, e.name());
	}

	public static <T extends Enum<T>> T getEnum(String key, NBTTagCompound tag, Class<T> clazz)
	{
		return Enum.valueOf(clazz, tag.getString(key));
	}

	public static void setUUID(NBTTagCompound tag, UUID uuid, String key)
	{
		tag.setLong(key + "most", uuid.getLeastSignificantBits());
		tag.setLong(key + "least", uuid.getMostSignificantBits());
	}

	public static UUID getUUID(NBTTagCompound tag, String key)
	{
		return new UUID(tag.getLong(key + "most"), tag.getLong(key + "least"));
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

	public static BlockPosDimension getBlockPosDimension(NBTTagCompound tag, String key)
	{
		if (tag.getBoolean(key + "_null"))
		{
			return null;
		}
		
		return new BlockPosDimension(tag.getInteger(key + "x"), tag.getInteger(key + "y"), tag.getInteger(key + "z"), tag.getInteger(key + "dimension"));
	}

	public static BlockPos readBlockPos(NBTTagCompound tag, String key)
	{
		if (tag.getBoolean(key + "_null"))
		{
			return null;
		}
		
		int[] pos = tag.getIntArray(key);

		return new BlockPos(pos[0], pos[1], pos[2]);
	}

	public static void writeBlockPos(NBTTagCompound tag, String key, BlockPos pos)
	{
		tag.setBoolean(key + "_null", pos == null);
		
		if (pos == null)
		{
			return;
		}
		
		tag.setIntArray(key, new int[] { pos.getX(), pos.getY(), pos.getZ() });
	}

	public static void setBlockPosDimension(NBTTagCompound tag, BlockPosDimension pos, String key)
	{
		tag.setBoolean(key + "_null", pos == null);
		
		if (pos == null)
		{
			return;
		}
		
		tag.setInteger(key + "x", pos.getX());
		tag.setInteger(key + "y", pos.getY());
		tag.setInteger(key + "z", pos.getZ());
		tag.setInteger(key + "dimension", pos.dimId());
	}
}
