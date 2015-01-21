package com.gildedgames.util.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.gildedgames.util.player.common.PlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.world.common.world.IWorld;

public class DataSet
{

	private static Random random = new Random();

	public static List<IWorld> iworlds()
	{
		List<IWorld> worlds = new ArrayList<IWorld>();
		worlds.add(new TestWorld(0));
		worlds.add(new TestWorld(1));
		worlds.add(new TestWorld(2));
		worlds.add(new TestWorld(3));
		return worlds;
	}

	public static List<IPlayerHook> iPlayerHooks(PlayerHookPool<TestPlayerHook> parentPool)
	{
		List<TestPlayerHook> players = new ArrayList<TestPlayerHook>();
		List<UUID> uuids = getUUIDs();
		for (UUID uuid : uuids)
		{
			players.add(parentPool.get(uuid));
		}

		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).idForCompare = i;
		}
		return new ArrayList<IPlayerHook>(players);
	}

	private static List<UUID> getUUIDs()
	{
		List<UUID> list = new ArrayList<UUID>();
		for (int i = 0; i < 5; i++)
		{
			list.add(new UUID(random.nextLong(), random.nextLong()));
		}
		return list;
	}
}
