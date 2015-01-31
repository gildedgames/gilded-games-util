package com.gildedgames.util.player.common;

import static org.junit.Assert.fail;

import java.util.UUID;

import net.minecraftforge.fml.relauncher.Side;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.player.common.player.PlayerProfile;
import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.player.TestPlayerHook;
import com.gildedgames.util.testutil.player.TestPlayerHookFactory;

public class PlayerHookPoolTest
{
	private PlayerHookPool<TestPlayerHook> create()
	{
		return new PlayerHookPool<TestPlayerHook>("test", new TestPlayerHookFactory(), Side.SERVER);
	}

	private PlayerHookPool<TestPlayerHook> dataSet()
	{
		PlayerHookPool<TestPlayerHook> hook = this.create();
		GGUtilDataSet.iPlayerHooks(hook);
		return hook;
	}

	@Test
	public void testClear()
	{
		PlayerHookPool<TestPlayerHook> hook = this.dataSet();
		hook.clear();
		Assert.assertTrue(hook.getPlayerHooks().isEmpty());
	}

	@Test
	public void testGetFactory()
	{
		TestPlayerHookFactory factory = new TestPlayerHookFactory();
		PlayerHookPool<TestPlayerHook> pool = new PlayerHookPool<TestPlayerHook>("test", factory, Side.SERVER);
		Assert.assertSame(factory, pool.getFactory());
	}

	@Test
	public void testGetName()
	{
		PlayerHookPool<TestPlayerHook> pool = new PlayerHookPool<TestPlayerHook>("test", new TestPlayerHookFactory(), Side.SERVER);
		Assert.assertEquals("test", pool.getName());
	}

	@Test
	public void testAdd()
	{
		PlayerHookPool<TestPlayerHook> pool = this.create();
		for (UUID uuid : GGUtilDataSet.uuids())
		{
			PlayerProfile profile = new PlayerProfile();
			profile.setUUID(uuid);
			TestPlayerHook hook = pool.getFactory().create(profile, pool);
			pool.add(hook);
			Assert.assertSame(hook, pool.get(uuid));
		}
	}

	@Test
	public void testGetUUID()
	{
		PlayerHookPool<TestPlayerHook> pool = this.create();
		for (UUID uuid : GGUtilDataSet.uuids())
		{
			PlayerProfile profile = new PlayerProfile();
			profile.setUUID(uuid);
			Assert.assertSame(pool.get(uuid), pool.get(uuid));
		}
	}

	@Test
	public void testSetPlayerHooks()
	{
		fail();
	}
}
