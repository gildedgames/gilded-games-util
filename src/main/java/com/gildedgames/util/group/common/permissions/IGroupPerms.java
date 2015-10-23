package com.gildedgames.util.group.common.permissions;

import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;

public interface IGroupPerms extends IO<IOBridge, IOBridge>
{

	String getName();

	String getDescription();

	boolean canInvite(Group group, GroupMember member, GroupMember inviter);

	boolean canChangeOwner(Group group, GroupMember newOwner, GroupMember changing);

	boolean canJoin(Group group, GroupMember member);

	boolean isVisible(Group group);

	boolean canRemoveGroup(Group group, GroupMember remover);

	boolean canRemoveMember(Group group, GroupMember toRemove, GroupMember remover);

	void onMemberRemoved(Group group, GroupMember removed);

	GroupMember owner();

}
