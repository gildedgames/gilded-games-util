package com.gildedgames.util.group.common.permissions;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.player.GroupMember;

public interface IGroupPerms extends NBT, IGroupHook
{

	String getName();

	String getDescription();

	boolean canInvite(GroupMember member, GroupMember inviter);

	boolean canChangeOwner(GroupMember newOwner, GroupMember changing);

	boolean canJoin(GroupMember member);

	boolean isVisible();

	boolean canRemoveGroup(GroupMember remover);

	boolean canRemoveMember(GroupMember toRemove, GroupMember remover);

}
