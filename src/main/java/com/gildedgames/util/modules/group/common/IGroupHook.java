package com.gildedgames.util.modules.group.common;

import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

public interface IGroupHook extends IO<IOBridge, IOBridge>
{
	void onMemberAdded(GroupMember member);

	void onMemberRemoved(GroupMember member);

	void onMemberInvited(GroupMember member);

	void onInviteRemoved(GroupMember member);
}
