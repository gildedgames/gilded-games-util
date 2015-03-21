package com.gildedgames.util.group.common;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.group.common.player.GroupMember;

public interface IGroupHook extends NBT
{
	void onMemberAdded(GroupMember member);

	void onMemberRemoved(GroupMember member);

	void onMemberInvited(GroupMember member);

	void onInviteRemoved(GroupMember member);
}
