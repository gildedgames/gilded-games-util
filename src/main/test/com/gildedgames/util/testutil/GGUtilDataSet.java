package com.gildedgames.util.testutil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.testutil.block.TestBlockState;
import com.gildedgames.util.testutil.io.TestMetadata;
import com.gildedgames.util.testutil.io.TestNBTFile;
import com.gildedgames.util.testutil.player.TestPlayerHook;
import com.gildedgames.util.testutil.player.TestPlayerHookFactory;
import com.gildedgames.util.testutil.world.TestWorld;
import com.gildedgames.util.testutil.world.TestWorldHook;
import com.gildedgames.util.world.common.world.IWorld;

public class GGUtilDataSet
{

	private static Random random = new Random();

	private static IOManager manager;

	public static List<IPlayerHookPool<?>> playerHookPools(List<UUID> uuids)
	{
		List<IPlayerHookPool<?>> pools = new ArrayList<IPlayerHookPool<?>>();
		for (int i = 0; i < 3; i++)
		{
			PlayerHookPool<TestPlayerHook> pool = new PlayerHookPool<TestPlayerHook>(Integer.toString(i), new TestPlayerHookFactory(), Side.SERVER);
			iPlayerHooks(pool, uuids);
			pools.add(pool);
		}
		return pools;
	}

	public static PlayerHookPool<TestPlayerHook> playerHookPool()
	{
		return new PlayerHookPool<TestPlayerHook>("test", new TestPlayerHookFactory(), Side.SERVER);
	}

	public static List<TestWorldHook> worldHooks()
	{
		List<TestWorldHook> worldHooks = new ArrayList<TestWorldHook>();
		int i = 0;
		for (IWorld world : iworlds())
		{
			worldHooks.add(new TestWorldHook(world, i++));
		}
		return worldHooks;
	}

	public static IWorld iworld()
	{
		return new TestWorld(0);
	}

	public static List<IWorld> iworlds()
	{
		List<IWorld> worlds = new ArrayList<IWorld>();
		worlds.add(new TestWorld(0));
		worlds.add(new TestWorld(1));
		worlds.add(new TestWorld(2));
		worlds.add(new TestWorld(3));
		return worlds;
	}

	public static List<IPlayerHook> iPlayerHooks(PlayerHookPool<TestPlayerHook> parentPool, List<UUID> uuids)
	{
		List<TestPlayerHook> players = new ArrayList<TestPlayerHook>();
		for (UUID uuid : uuids)
		{
			players.add(parentPool.get(uuid));
		}
		return new ArrayList<IPlayerHook>(players);
	}

	public static List<IPlayerHook> iPlayerHooks(PlayerHookPool<TestPlayerHook> parentPool)
	{
		return iPlayerHooks(parentPool, uuids());
	}

	public static List<UUID> uuids()
	{
		List<UUID> list = new ArrayList<UUID>();
		for (int i = 0; i < 5; i++)
		{
			list.add(uuid());
		}
		return list;
	}

	public static UUID uuid()
	{
		return new UUID(random.nextLong(), random.nextLong());
	}

	public static IPlayerHook playerHook(PlayerHookPool<TestPlayerHook> parentPool)
	{
		return parentPool.get(uuid());
	}

	public static List<TestNBTFile> nbtFiles()
	{
		List<TestNBTFile> list = new ArrayList<TestNBTFile>();
		for (int i = 4; i < 15; i++)
		{
			list.add(new TestNBTFile(i, i + 100));
		}
		return list;
	}

	public static List<TestMetadata> metadata()
	{
		List<TestMetadata> list = new ArrayList<TestMetadata>();
		for (int i = 0; i < 5; i++)
		{
			list.add(new TestMetadata(i));
		}
		return list;
	}

	public static File fileFor(String path)
	{
		return new File(System.getProperty("user.dir") + File.separator + "eclipse" + File.separator + "test", path);
	}

	public static String testPath()
	{
		return System.getProperty("user.dir") + File.separator + "eclipse" + File.separator + "test";
	}

	static
	{
		manager = new IOManagerDefault("testgg");
		IORegistry registry = manager.getRegistry();
		registry.registerClass(TestWorldHook.class, 0);
		registry.registerClass(TestPlayerHook.class, 1);
		registry.registerClass(TestPlayerHookFactory.class, 2);
		registry.registerClass(TestMetadata.class, 3);
		registry.registerClass(TestNBTFile.class, 4);
		IOCore.io().registerManager(manager);
	}

	public static IOManager iomanager()
	{
		return manager;
	}

	public static IBlockState blockState()
	{
		return TestBlockState.getRandomState();
	}
}
