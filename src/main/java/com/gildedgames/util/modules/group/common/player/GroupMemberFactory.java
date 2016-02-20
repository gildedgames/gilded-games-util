package com.gildedgames.util.modules.group.common.player;

import com.gildedgames.util.modules.player.common.IPlayerHookFactory;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import com.gildedgames.util.modules.player.common.player.IPlayerProfile;

public class GroupMemberFactory implements IPlayerHookFactory<GroupMember>
{

	@Override
	public GroupMember create(IPlayerProfile profile, IPlayerHookPool<GroupMember> pool)
	{
		return new GroupMember(profile, pool);
	}

}
