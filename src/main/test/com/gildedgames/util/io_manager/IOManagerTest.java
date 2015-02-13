package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.overhead.IOFileController;
import com.gildedgames.util.io_manager.overhead.IOVolatileController;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
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
		return new IOManagerDefault("test");
	}

	@Test
	public void testRegisterAndGetClass()
	{
		IOManager manager = this.create();
		IORegistry registry = manager.getRegistry();
		registry.registerClass(TestPlayerHookFactory.class, 1);
		Assert.assertEquals(registry.getID(TestPlayerHookFactory.class), 1);
		Assert.assertEquals(registry.getID(new TestPlayerHookFactory()), 1);
		Assert.assertEquals(registry.getClass(manager.getID(), 1), TestPlayerHookFactory.class);
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
				IOFileController serializer = manager.getFileController();
				serializer.writeFile(file, object, new NBTFactory());
				TestNBTFile readBack = (TestNBTFile) serializer.readFile(file, new NBTFactory());
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
	public void testReadWriteMetadata()
	{
		List<TestNBTFile> files = GGUtilDataSet.nbtFiles();
		IOManager manager = GGUtilDataSet.iomanager();
		for (TestNBTFile object : files)
		{
			File file = GGUtilDataSet.fileFor(object.toString() + ".test");
			try
			{
				IOFileController serializer = manager.getFileController();
				serializer.writeFile(file, object, new NBTFactory());
				TestMetadata readBack = (TestMetadata) serializer.readFileMetadata(file, new NBTFactory());
				Assert.assertEquals(object.getSubData().get(), readBack);
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
		IOVolatileController serializer = manager.getVolatileController();
		TestMetadata clone = serializer.clone(new NBTFactory(), files);
		Assert.assertEquals(files, clone);
	}

	@Test
	public void testCreate()
	{
		IOManager dataset = GGUtilDataSet.iomanager();
		IORegistry registry = dataset.getRegistry();
		Object o = registry.create(dataset.getID(), 1);
		Assert.assertTrue(o instanceof TestPlayerHook);
		o = registry.create(dataset.getID(), 3, new TestConstructor());
		Assert.assertTrue(o instanceof TestMetadata && ((TestMetadata) o).id == 1);
	}

}
