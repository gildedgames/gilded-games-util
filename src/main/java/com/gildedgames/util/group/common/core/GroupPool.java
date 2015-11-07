package com.gildedgames.util.group.common.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.IGroupPoolListener;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.util.IOUtil;

import net.minecraft.entity.player.EntityPlayer;

public abstract class GroupPool implements IO<IOBridge, IOBridge>
{

	protected final String id;

	protected List<Group> groups = new ArrayList<Group>();

	protected List<IGroupPoolListener<?>> listeners = new ArrayList<IGroupPoolListener<?>>();

	public GroupPool(String id)
	{
		this.id = id;
	}

	public void addListener(IGroupPoolListener<?> listener)
	{
		this.listeners.add(listener);
	}

	public String getID()
	{
		return this.id;
	}

	public List<Group> getGroups()
	{
		return this.groups;
	}

	public void setGroups(List<Group> groups)
	{
		this.groups = groups;
	}

	public void clear()
	{
		this.groups.clear();
	}

	protected List<IGroupHook> createHooks(Group group)
	{
		List<IGroupHook> list = new ArrayList<IGroupHook>(this.listeners.size());
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			IGroupHook hook = listener.createGroupHook(group);
			if (hook != null)
			{
				list.add(hook);
			}
		}
		return list;
	}

	protected void addGroupDirectly(Group group)
	{
		UtilCore.debugPrint("Adding group " + group.getName());
		this.groups.add(group);
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			listener.onGroupAdded(group);
		}
	}

	protected void removeGroupDirectly(Group group)
	{
		UtilCore.debugPrint("Removing group " + group.getName());
		this.groups.remove(group);
		if (group.hasMemberData())
		{
			for (GroupMember member : group.getMemberData())
			{
				member.leaveGroup(group);
			}
		}
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			listener.onGroupRemoved(group);
		}
	}

	protected void changeGroupInfoDirectly(Group group, GroupInfo groupInfo)
	{
		UtilCore.debugPrint("Changing info of group " + group.getName());
		GroupInfo infoOld = new GroupInfo(group.getName(), group.getPermissions());
		group.setGroupInfo(groupInfo);
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			listener.onGroupInfoChanged(group, infoOld, groupInfo);
		}
	}

	protected void addMemberDirectly(Group group, GroupMember member)
	{
		this.assertHasMemberData(group);
		UtilCore.debugPrint("Adding member " + member.getProfile().getUsername() + " to group " + group.getName());
		member.joinGroup(group);
		group.getMemberData().join(member);
	}

	protected void removeMemberDirectly(Group group, GroupMember member)
	{
		this.assertHasMemberData(group);
		UtilCore.debugPrint("Removing member " + member.getProfile().getUsername() + " from group " + group.getName());
		member.leaveGroup(group);
		group.getMemberData().leave(member);
	}

	protected void inviteDirectly(Group group, GroupMember member, GroupMember inviter)
	{
		this.assertHasMemberData(group);
		UtilCore.debugPrint("Inviting member " + member.getProfile().getUsername() + " to group " + group.getName());
		group.getMemberData().invite(member);
		member.addInvite(group);
	}

	protected void removeInvitationDirectly(Group group, GroupMember member)
	{
		this.assertHasMemberData(group);
		UtilCore.debugPrint("Removing invitation of member " + member.getProfile().getUsername() + " from group " + group.getName());
		group.getMemberData().removeInvitation(member);
		member.removeInvite(group);
	}

	protected void assertHasMemberData(Group group)
	{
		if (!group.hasMemberData())
		{
			throw new IllegalStateException("Trying to modify group of which memberdata is unknown");
		}
	}

	public abstract Group create(String name, EntityPlayer creating, IGroupPerms perms);

	public abstract void addMember(UUID player, Group group);

	public abstract void removeMember(UUID player, Group group);

	public abstract void invite(UUID invited, UUID inviter, Group group);

	public abstract void removeInvitation(UUID player, Group group);

	public abstract void remove(Group group);

	protected boolean assertValidGroup(Group group)
	{
		if (!this.groups.contains(group))
		{
			UtilCore.print("Trying to manage group that is not in this pool: " + group.getName());
			return false;
		}
		return true;
	}

	protected boolean assertMemberIn(Group group, UUID player)
	{
		if (!group.hasMemberData() || !group.getMemberData().contains(player))
		{
			UtilCore.print("Member is not in the group like excepted. Group: " + group.getName() + " player: " + GroupCore.locate().getPlayers().get(player).getProfile().getUsername());
			return false;
		}
		return true;
	}

	public Group get(String name)
	{
		for (Group group : this.groups)
		{
			if (group.getName().equalsIgnoreCase(name))
			{
				return group;
			}
		}

		return null;
	}

	@Override
	public void read(IOBridge input)
	{
		this.groups = IOUtil.getIOList("groups", input);
	}

	@Override
	public void write(IOBridge output)
	{
		IOUtil.setIOList("groups", this.groups, output);
	}

}
