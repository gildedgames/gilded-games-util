package com.gildedgames.util.universe;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.player.PlayerUniverse;

public class UniverseServices
{
	
	public IPlayerHookPool<PlayerUniverse> players;
	
	public UniverseAPI api;

	public UniverseServices()
	{
		
	}
	
	public IPlayerHookPool<PlayerUniverse> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<PlayerUniverse>(UtilCore.MOD_ID, PlayerUniverse.class);
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
