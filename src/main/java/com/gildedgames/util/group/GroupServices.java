package com.gildedgames.util.group;

import net.minecraftforge.fml.relauncher.Side;

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

public class GroupServices
{

	private IPlayerHookPool<GroupMember> players;

	private Side side;
	
	private IGroupController groupController;
	
	private IGroupPoolController groupPoolController;

	public GroupServices(Side side)
	{
		this.side = side;
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
