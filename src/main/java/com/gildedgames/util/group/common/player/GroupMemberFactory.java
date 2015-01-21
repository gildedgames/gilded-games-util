package com.gildedgames.util.group.common.player;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class GroupMemberFactory implements IPlayerHookFactory<GroupMember>
{

	@Override
	public GroupMember create(IPlayerProfile profile, IPlayerHookPool<GroupMember> pool)
	{
		return new GroupMember(profile, pool);
	}

}
