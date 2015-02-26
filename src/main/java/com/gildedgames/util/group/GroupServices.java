package com.gildedgames.util.group;

import com.gildedgames.util.group.common.IGroup;
import com.gildedgames.util.group.common.IGroupPool;
import com.gildedgames.util.group.common.network.GroupController;
import com.gildedgames.util.group.common.network.GroupPoolController;
import com.gildedgames.util.group.common.network.IGroupController;
import com.gildedgames.util.group.common.network.IGroupPoolController;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.group.common.player.GroupMemberFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class GroupServices
{

	private IPlayerHookPool<GroupMember> players;

	private final Side side;
	
	private IGroupController groupController;
	
	private IGroupPoolController groupPoolController;
	
	private List<IGroupPool> pools;

	public GroupServices(Side side)
	{
		this.side = side;
	}
	
	public List<IGroupPool> getPools()
	{
		if (this.pools == null)
		{
			this.pools = new ArrayList<IGroupPool>();
		}
		
		return this.pools;
	}

	public IGroupPool getFromID(String id)
	{
		for (IGroupPool pool : this.getPools())
		{
			if (pool != null && pool.getID().equals(id))
			{
				return pool;
			}
		}
		
		return null;
	}
	
	public void registerPool(IGroupPool pool)
	{
		this.getPools().add(pool);
	}
	
	public IGroupPoolController talkTo(IGroupPool groupPool)
	{
		if (this.groupPoolController == null)
		{
			this.groupPoolController = new GroupPoolController(this.side);
		}
		
		this.groupPoolController.setTargetPool(groupPool);
		
		return this.groupPoolController;
	}
	
	public IGroupController talkTo(IGroup group)
	{
		if (this.groupController == null)
		{
			this.groupController = new GroupController(this.side);
		}
		
		this.groupController.setTargetGroup(group);
		
		return this.groupController;
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
