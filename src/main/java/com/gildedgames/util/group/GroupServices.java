package com.gildedgames.util.group;

import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

public class GroupServices
{
	
	private IPlayerHookPool<GroupMember> players;

	public GroupServices()
	{
		
	}
	
	public IPlayerHookPool<GroupMember> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<GroupMember>("group", GroupMember.class);
		}
		
		return this.players;
	}
	
}
