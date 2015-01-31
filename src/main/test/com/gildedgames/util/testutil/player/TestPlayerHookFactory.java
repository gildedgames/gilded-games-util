package com.gildedgames.util.testutil.player;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class TestPlayerHookFactory implements IPlayerHookFactory<TestPlayerHook>
{

	private static int id = 0;

	@Override
	public TestPlayerHook create(IPlayerProfile profile, IPlayerHookPool<TestPlayerHook> pool)
	{
		return new TestPlayerHook(profile, pool, id++);
	}

}
