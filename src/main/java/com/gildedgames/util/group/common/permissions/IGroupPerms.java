package com.gildedgames.util.group.common.permissions;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;

public interface IGroupPerms extends NBT
{

	String getName();

	String getDescription();

	boolean canInvite(Group group, GroupMember member, GroupMember inviter);

	boolean canChangeOwner(Group group, GroupMember newOwner, GroupMember changing);

	boolean canJoin(Group group, GroupMember member);

	boolean isVisible(Group group);

	boolean doAutoReassignOwner(Group group);

	boolean canRemoveGroup(Group group, GroupMember remover);

	boolean canRemoveMember(Group group, GroupMember toRemove, GroupMember remover);

	GroupMember chooseNewOwner(Group group);

}
