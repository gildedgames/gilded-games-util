package com.gildedgames.util.io_manager;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.io_manager.util.nbt.NBTFactory;
import com.gildedgames.util.io_manager.util.nbt.NBTManager;
import com.gildedgames.util.testutil.DataSet;
import com.gildedgames.util.testutil.TestMetadata;
import com.gildedgames.util.testutil.TestNBTFile;
import com.gildedgames.util.testutil.TestPlayerHook;
import com.gildedgames.util.testutil.TestPlayerHookFactory;
import com.gildedgames.util.testutil.TestWorldHook;

public class IOManagerTest
{

	private NBTManager create()
	{
		return new NBTManager();
	}

	private NBTManager dataSet()
	{
		NBTManager manager = this.create();
		manager.register(TestWorldHook.class, 0);
		manager.register(TestPlayerHook.class, 1);
		manager.register(TestPlayerHookFactory.class, 2);
		manager.register(TestMetadata.class, 3);
		manager.register(TestNBTFile.class, 4);
		return manager;
	}

	@Test
	public void testRegisterAndGetClass()
	{
		NBTManager manager = this.create();
		manager.register(TestPlayerHookFactory.class, 0);
		Assert.assertEquals(manager.getID(TestPlayerHookFactory.class), 0);
		Assert.assertEquals(manager.getID(new TestPlayerHookFactory()), 0);
		Assert.assertEquals(manager.getClassFromID(0), TestPlayerHookFactory.class);
	}

	@Test
	public void testReadWriteOne()
	{
		List<TestNBTFile> files = DataSet.nbtFiles();
		NBTManager manager = this.dataSet();
		for (TestNBTFile object : files)
		{
			File file = DataSet.fileFor(object.toString() + ".test");
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
	public void testReadFileFileIReaderWriterFactoryOfFILEREADERWRITER()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileStringFILEIReaderWriterFactoryOfFILEREADERWRITER()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileMetadata()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testWriteFileStringFILEIReaderWriterFactoryOfFILEREADERWRITER()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCheckFileExists()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetExtensionFiles()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetFileFromName()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetFoldersInDirectory()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetFilesWithExtensionFileListOfString()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadIClassOfQextendsIOOfIQ()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testWrite()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCloneOT()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCreate()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCreateFromIDInt()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetIDObject()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassFromID()
	{
		fail("Not yet implemented");
	}

}
