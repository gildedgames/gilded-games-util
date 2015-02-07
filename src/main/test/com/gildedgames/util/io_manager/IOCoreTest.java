package com.gildedgames.util.io_manager;

import static org.junit.Assert.fail;

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
			IOCore.io().write(tag, factory, file);
			TestNBTFile read = IOCore.io().read(tag, factory);
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

	@Test
	public void testGetSetVolatile()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCloneIOFactoryOfQIOT()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileFileIOFactoryOfFILEIO()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileFileIOFactoryOfFILEIOIConstructor()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileFileFILEIOFactoryOfFILEIO()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testWriteFile()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetRegistryID()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterClass()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterBehavior()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCreateClassOfTIConstructor()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCreateStringInt()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCreateStringIntIConstructor()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassStringInt()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetIDClassOfQ()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetIDObject()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsClassRegistered()
	{
		fail("Not yet implemented");
	}

}
