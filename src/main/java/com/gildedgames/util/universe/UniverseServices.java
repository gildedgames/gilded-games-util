package com.gildedgames.util.universe;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.player.PlayerUniverse;
import com.gildedgames.util.universe.common.player.PlayerUniverseFactory;

import cpw.mods.fml.relauncher.Side;

public class UniverseServices
{

	public IPlayerHookPool<PlayerUniverse> players;

	public UniverseAPI api;

	private Side side;

	public UniverseServices(Side side)
	{
		this.side = side;
	}

	public IPlayerHookPool<PlayerUniverse> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<PlayerUniverse>("universe", new PlayerUniverseFactory(), this.side);
		}

		return this.players;
	}

	public UniverseAPI getAPI()
	{
		if (this.api == null)
		{
			this.api = new UniverseAPI();
		}

		return this.api;
	}

}
