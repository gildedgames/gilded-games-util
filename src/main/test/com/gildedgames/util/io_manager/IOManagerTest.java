package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.io_manager.util.nbt.NBTFactory;
import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.io.TestConstructor;
import com.gildedgames.util.testutil.io.TestMetadata;
import com.gildedgames.util.testutil.io.TestNBTFile;
import com.gildedgames.util.testutil.player.TestPlayerHook;
import com.gildedgames.util.testutil.player.TestPlayerHookFactory;

public class IOManagerTest
{

	private IOManager create()
	{
		return new IOManager();
	}

	@Test
	public void testRegisterAndGetClass()
	{
		IOManager manager = this.create();
		manager.register(TestPlayerHookFactory.class, 1);
		Assert.assertEquals(manager.getIDFromClass(TestPlayerHookFactory.class), 1);
		Assert.assertEquals(manager.getID(new TestPlayerHookFactory()), 1);
		Assert.assertEquals(manager.getClassFromID(1), TestPlayerHookFactory.class);
	}

	@Test
	public void testReadWriteOne()
	{
		List<TestNBTFile> files = GGUtilDataSet.nbtFiles();
		IOManager manager = GGUtilDataSet.iomanager();
		for (TestNBTFile object : files)
		{
			File file = GGUtilDataSet.fileFor(object.toString() + ".test");
			try
			{
				manager.writeFile(file, object, new NBTFactory());
				TestNBTFile readBack = (TestNBTFile) manager.readFile(file, new NBTFactory());
				Assert.assertEquals(object, readBack);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}

	@Test
	public void testReadWriteTwo()
	{
		List<TestNBTFile> files = GGUtilDataSet.nbtFiles();
		IOManager manager = GGUtilDataSet.iomanager();
		for (TestNBTFile object : files)
		{
			File file = GGUtilDataSet.fileFor(object.toString() + ".test");
			try
			{
				manager.writeFile(file, object, new NBTFactory());
				TestNBTFile toReadIn = new TestNBTFile(-1, -1);
				manager.readFile(file, toReadIn, new NBTFactory());
				Assert.assertEquals(object, toReadIn);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}

	@Test
	public void testReadWriteMetadata()
	{
		List<TestNBTFile> files = GGUtilDataSet.nbtFiles();
		IOManager manager = GGUtilDataSet.iomanager();
		for (TestNBTFile object : files)
		{
			File file = GGUtilDataSet.fileFor(object.toString() + ".test");
			try
			{
				manager.writeFile(file, object, new NBTFactory());
				TestMetadata readBack = (TestMetadata) manager.readFileMetadata(file, new NBTFactory());
				Assert.assertEquals(object.getMetadata().get(), readBack);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
	}

	@Test
	public void testClone() throws IOException
	{
		TestMetadata files = new TestMetadata(1);
		IOManager manager = GGUtilDataSet.iomanager();
		TestMetadata clone = manager.clone(new NBTTagCompound(), files);
		Assert.assertEquals(files, clone);
	}

	@Test
	public void testCreate()
	{
		IOManager dataset = GGUtilDataSet.iomanager();
		Object o = dataset.createFromID(1);
		Assert.assertTrue(o instanceof TestPlayerHook);
		o = dataset.createFromID(3, new TestConstructor());
		Assert.assertTrue(o instanceof TestMetadata && ((TestMetadata) o).id == 1);
	}

}
