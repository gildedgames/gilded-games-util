package com.gildedgames.util.testutil.io;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.io.NBT;

public class NBTTest
{
	public static <T extends NBT> void doTest(NBTTestFactory<T> factory)
	{
		NBTTagCompound tag = new NBTTagCompound();
		T expected = factory.createExpected();
		expected.write(tag);
		T empty = factory.createEmpty();
		empty.read(tag);
		factory.testCompare(expected, empty);
	}

	public interface NBTTestFactory<T extends NBT>
	{
		T createEmpty();

		T createExpected();

		void testCompare(T expected, T actual);
	}
}
