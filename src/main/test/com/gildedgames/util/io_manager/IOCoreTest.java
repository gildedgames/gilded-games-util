package com.gildedgames.util.io_manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.io_manager.exceptions.IOManagerTakenException;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.player.PlayerServicesTest;
import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.io.TestNBTFile;

public class IOCoreTest
{

	@Test
	public void testAddManager()
	{
		IOManager manager1 = new IOManagerDefault("test");
		IOCore.io().registerManager(manager1);
		IOManager found = IOCore.io().getManager("test");
		Assert.assertSame(manager1, found);

		manager1.getRegistry().registerClass(PlayerServicesTest.class, 5);
		found = IOCore.io().getManager(PlayerServicesTest.class);
		Assert.assertSame(manager1, found);

		try
		{
			IOCore.io().registerManager(new IOManagerDefault("test"));
			Assert.fail();
		}
		catch (IOManagerTakenException e)
		{
		}
	}

	@Test
	public void testReadWriteVolatile()
	{
		GGUtilDataSet.iomanager();
		List<TestNBTFile> files = GGUtilDataSet.nbtFiles();
		for (TestNBTFile file : files)
		{
			NBTTagCompound tag = new NBTTagCompound();
			NBTFactory factory = new NBTFactory();
			IOCore.io().set("yolo", tag, factory, file);
			TestNBTFile read = IOCore.io().get("yolo", tag, factory);
			Assert.assertEquals(file, read);
		}
	}

	@Test
	public void testReadWrite()
	{
		GGUtilDataSet.iomanager();
		List<TestNBTFile> files = GGUtilDataSet.nbtFiles();
		for (TestNBTFile file : files)
		{
			NBTFactory factory = new NBTFactory();
			File f = GGUtilDataSet.fileFor("testIOCore");
			try
			{
				IOCore.io().writeFile(f, file, factory);
				TestNBTFile readBack = (TestNBTFile) IOCore.io().readFile(f, factory);
				Assert.assertEquals(file, readBack);
			}
			catch (IOException e)
			{
				Assert.fail();
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testRegistration()
	{
		IOCore.io().registerClass(IOCoreTest.class, 5);
		Assert.assertNotNull(IOCore.io().getManager(IOCoreTest.class));
	}

}
