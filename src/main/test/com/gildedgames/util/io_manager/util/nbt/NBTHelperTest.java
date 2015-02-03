package com.gildedgames.util.io_manager.util.nbt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.testutil.GGUtilDataSet;

public class NBTHelperTest
{

	private NBTTagCompound tag()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("test", 1);
		return tag;
	}

	@Test
	public void testInputOutputNBT() throws IOException
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(byteStream);
		NBTTagCompound tag = this.tag();
		NBTHelper.writeOutputNBT(tag, outputStream);
		outputStream.close();
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteStream.toByteArray());
		DataInputStream inputStream = new DataInputStream(byteInputStream);
		NBTTagCompound tag2 = NBTHelper.readInputNBT(inputStream);
		Assert.assertTrue(tag2.getInteger("test") == 1);
		inputStream.close();
	}

	@Test
	public void testSaveLoad() throws IOException
	{
		File file = GGUtilDataSet.fileFor("nbtHelperTest");
		NBTTagCompound tag = this.tag();
		NBTHelper.save(file, tag);
		NBTTagCompound tag2 = NBTHelper.load(file);
		Assert.assertTrue(tag2.getInteger("test") == 1);
	}
}
