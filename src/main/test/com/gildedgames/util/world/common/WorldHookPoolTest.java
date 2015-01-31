/**
 * 
 */
package com.gildedgames.util.world.common;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.world.TestWorld;
import com.gildedgames.util.testutil.world.TestWorldHook;
import com.gildedgames.util.testutil.world.TestWorldHookFactory;
import com.gildedgames.util.world.common.world.IWorld;

/**
 * @author Emile
 *
 */
public class WorldHookPoolTest
{

	private static String poolName = "test";

	private WorldHookPool<TestWorldHook> createPool()
	{
		return new WorldHookPool<TestWorldHook>(new TestWorldHookFactory(GGUtilDataSet.iworlds()), poolName);
	}

	private void fillWithData(WorldHookPool<?> pool)
	{
		for (IWorld world : GGUtilDataSet.iworlds())
		{
			pool.get(world);
		}
	}

	@Test
	public void testReadAndWrite()
	{
		NBTTagCompound tag = new NBTTagCompound();
		WorldHookPool<TestWorldHook> read = this.createPool();
		WorldHookPool<TestWorldHook> write = this.createPool();
		this.fillWithData(write);
		write.write(tag);
		read.read(tag);
		Assert.assertEquals("WorldHooks aren't written or read correctly", write.getWorlds(), read.getWorlds());

	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#get(com.gildedgames.util.worldhook.common.world.IWorld)}.
	 */
	@Test
	public void testGetIWorld()
	{
		TestWorld testWorld = new TestWorld(0);
		WorldHookPool<TestWorldHook> testPool = this.createPool();
		Assert.assertSame("Retrieval of worldHooks is broken", testPool.get(testWorld), testPool.get(testWorld));
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#clear()}.
	 */
	@Test
	public void testClear()
	{
		WorldHookPool<?> testPool = this.createPool();
		this.fillWithData(testPool);
		testPool.clear();
		Assert.assertTrue(testPool.getWorlds().isEmpty());
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#getPoolName()}.
	 */
	@Test
	public void testGetPoolName()
	{
		WorldHookPool<?> testPool = this.createPool();
		Assert.assertEquals(poolName, testPool.getPoolName());
	}

}
