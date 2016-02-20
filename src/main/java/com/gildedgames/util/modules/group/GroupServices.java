package com.gildedgames.util.modules.group;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.modules.group.common.core.GroupPool;
import com.gildedgames.util.modules.group.common.core.GroupPoolClient;
import com.gildedgames.util.modules.group.common.core.GroupPoolServer;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.modules.group.common.player.GroupMemberFactory;
import com.gildedgames.util.modules.group.common.util.DefaultSettings;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import com.gildedgames.util.modules.player.common.PlayerHookPool;

import net.minecraftforge.fml.relauncher.Side;

public class GroupServices
{

	private IPlayerHookPool<GroupMember> players;

	private final Side side;

	private List<GroupPool> pools;

	private GroupPool defaultPool;

	public GroupServices(Side side)
	{
		this.side = side;
	}

	public List<GroupPool> getPools()
	{
		if (this.pools == null)
		{
			this.pools = new ArrayList<>();
		}

		return this.pools;
	}

	public GroupPool getPoolFromID(String id)
	{
		for (GroupPool pool : this.getPools())
		{
			if (pool != null && pool.getID().equals(id))
			{
				return pool;
			}
		}

		return null;
	}

	protected void registerPool(GroupPool pool)
	{
		this.getPools().add(pool);
	}

	public IPlayerHookPool<GroupMember> getPlayers()
	{
		if (this.players == null)
		{
			this.players = new PlayerHookPool<>("group", new GroupMemberFactory(), this.side);
		}

		return this.players;
	}

	public GroupPool getDefaultPool()
	{
		if (this.defaultPool == null)
		{
			if (this.side == Side.CLIENT)
			{
				this.defaultPool = new GroupPoolClient("default");
			}
			else
			{
				this.defaultPool = new GroupPoolServer("default", new DefaultSettings());
			}
		}
		return this.defaultPool;
	}

}
