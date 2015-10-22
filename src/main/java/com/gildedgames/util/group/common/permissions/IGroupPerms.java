package com.gildedgames.util.group.common.permissions;

import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

public interface IGroupPerms extends IO<IOBridge, IOBridge>, IGroupHook
{

	String getName();

	String getDescription();

	boolean canInvite(GroupMember member, GroupMember inviter);

	boolean canChangeOwner(GroupMember newOwner, GroupMember changing);

	boolean canJoin(GroupMember member);

	boolean isVisible();

	boolean canRemoveGroup(GroupMember remover);

	boolean canRemoveMember(GroupMember toRemove, GroupMember remover);

	GroupMember owner();

}
