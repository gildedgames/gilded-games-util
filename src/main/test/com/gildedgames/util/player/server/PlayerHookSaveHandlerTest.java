package com.gildedgames.util.player.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.testutil.GGUtilDataSet;

public class PlayerHookSaveHandlerTest
{

	/**
	 * This test writes several playerhooks to files, reads it back and calls .equals on the original data set
	 * and the read back one
	 */
	@Test
	public void testWriteAndRead()
	{
		List<UUID> uuids = GGUtilDataSet.uuids();
		List<IPlayerHookPool<?>> pools = GGUtilDataSet.playerHookPools(uuids);
		PlayerHookSaveHandler saveHandler = new PlayerHookSaveHandler();
		Map<IPlayerHookPool<?>, List<IPlayerHook>> map = new HashMap<IPlayerHookPool<?>, List<IPlayerHook>>();
		for (IPlayerHookPool<?> pool : pools)
		{
			List<IPlayerHook> oldHooks = new ArrayList<IPlayerHook>();
			for (UUID uuid : uuids)
			{
				IPlayerHook hook = pool.get(uuid);
				oldHooks.add(hook);
			}
			map.put(pool, oldHooks);
		}
		for (UUID uuid : uuids)
		{
			saveHandler.writePlayerData(uuid, pools);
		}
		for (IPlayerHookPool<?> pool : pools)
		{
			pool.clear();
		}
		for (UUID uuid : uuids)
		{
			saveHandler.readPlayerData(uuid, null, pools);
		}
		for (IPlayerHookPool<?> pool : pools)
		{
			List<IPlayerHook> newSet = new ArrayList<IPlayerHook>();
			newSet.addAll(pool.getPlayerHooks());
			List<IPlayerHook> actual = map.get(pool);
			Assert.assertTrue(newSet.containsAll(actual) && actual.containsAll(newSet));
		}
	}

}
