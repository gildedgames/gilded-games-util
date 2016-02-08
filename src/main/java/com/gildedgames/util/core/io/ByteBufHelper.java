package com.gildedgames.util.core.io;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class ByteBufHelper
{
	public static ItemStack[] readItemStacks(ByteBuf buf)
	{
		int size = buf.readInt();

		ItemStack[] stacks = new ItemStack[size];

		PacketBuffer buffer = new PacketBuffer(buf);

		for (int i = 0; i < size; i++)
		{
			try
			{
				stacks[i] = buffer.readItemStackFromBuffer();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return stacks;
	}

	public static void  writeItemStacks(ByteBuf buf, ItemStack[] stacks)
	{
		buf.writeInt(stacks.length);

		PacketBuffer buffer = new PacketBuffer(buf);

		for (ItemStack stack : stacks)
		{
			buffer.writeItemStackToBuffer(stack);
		}
	}
}
