/**
 * 
 */
package com.gildedgames.util.worldhook.common;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.worldhook.common.world.IWorld;

/**
 * @author Emile
 *
 */
public class WorldHookPoolTest
{

	private static String poolName = "test";

	private List<IWorld> worldDataSet()
	{
		List<IWorld> worlds = new ArrayList<IWorld>();
		worlds.add(new TestWorld());
		worlds.add(new TestWorld());
		worlds.add(new TestWorld());
		worlds.add(new TestWorld());
		return worlds;
	}

	private WorldHookPool<TestHook> createPool()
	{
		return new WorldHookPool<TestHook>(new TestHookFactory(), poolName);
	}

	private void fillWithData(WorldHookPool<?> pool)
	{
		for (IWorld world : this.worldDataSet())
		{
			pool.get(world);
		}
	}

	@Test
	public void testReadAndWrite()
	{
		NBTTagCompound tag = new NBTTagCompound();
		WorldHookPool<TestHook> read = this.createPool();
		WorldHookPool<TestHook> write = this.createPool();
		this.fillWithData(write);
		write.write(tag);
		read.read(tag);
		Assert.assertEquals(write.getWorlds(), read.getWorlds());

	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#read(net.minecraft.nbt.NBTTagCompound)}.
	 */
	@Test
	public void testRead()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#get(net.minecraft.world.World)}.
	 */
	@Test
	public void testGetWorld()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#get(com.gildedgames.util.worldhook.common.world.IWorld)}.
	 */
	@Test
	public void testGetIWorld()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#getWorlds()}.
	 */
	@Test
	public void testGetWorlds()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#clear()}.
	 */
	@Test
	public void testClear()
	{
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.gildedgames.util.worldhook.common.WorldHookPool#getPoolName()}.
	 */
	@Test
	public void testGetPoolName()
	{
		fail("Not yet implemented");
	}

	private class TestHookFactory implements IWorldHookFactory<TestHook>
	{

		@Override
		public TestHook create(IWorld w)
		{
			return new TestHook(w);
		}

	}

	private class TestHook implements IWorldHook
	{

		IWorld w;

		private TestHook(IWorld w)
		{
			this.w = w;
		}

		@Override
		public void write(NBTTagCompound output)
		{
		}

		@Override
		public void read(NBTTagCompound input)
		{
		}

		@Override
		public void onLoad()
		{
		}

		@Override
		public void onUnload()
		{
		}

		@Override
		public void onSave()
		{
		}

		@Override
		public void onUpdate()
		{
		}

		@Override
		public IWorld getWorld()
		{
			return this.w;
		}

	}

	private class TestWorld implements IWorld
	{

		@Override
		public IBlockAccess getBlockAccess()
		{
			return null;
		}

		@Override
		public IBlockState getBlockState(BlockPos pos)
		{
			return null;
		}

		@Override
		public boolean setBlockState(BlockPos pos, IBlockState state)
		{
			return false;
		}

		@Override
		public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
		{
			return false;
		}

		@Override
		public boolean isAirBlock(BlockPos pos)
		{
			return false;
		}

		@Override
		public boolean setBlockToAir(BlockPos pos)
		{
			return false;
		}

		@Override
		public boolean destroyBlock(BlockPos pos, boolean dropBlock)
		{
			return false;
		}

		@Override
		public int getDimensionID()
		{
			return 0;
		}

		@Override
		public Random getRandom()
		{
			return null;
		}

		@Override
		public TileEntity getTileEntity(BlockPos pos)
		{
			return null;
		}

		@Override
		public void setTileEntity(BlockPos pos, TileEntity tileEntity)
		{
		}

		@Override
		public boolean isWrapperFor(int dimId, boolean isRemote)
		{
			return false;
		}

		@Override
		public boolean isRemote()
		{
			return false;
		}

		@Override
		public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
		{
			return false;
		}

	}

}
