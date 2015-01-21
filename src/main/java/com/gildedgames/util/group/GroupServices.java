package com.gildedgames.util.group;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.group.common.player.GroupMemberFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

public class GroupServices
{

	private IPlayerHookPool<GroupMember> players;

	private Side side;

	public GroupServices(Side side)
	{
		this.side = side;
	}

	public IPlayerHookPool<GroupMember> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<GroupMember>("group", new GroupMemberFactory(), this.side);
		}

		return this.players;
	}

}
