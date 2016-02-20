package com.gildedgames.util.modules.group.common.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.IGroupHook;
import com.gildedgames.util.modules.group.common.IGroupPoolListener;
import com.gildedgames.util.modules.group.common.permissions.IGroupPerms;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.util.IOUtil;

import net.minecraft.entity.player.EntityPlayer;

public abstract class GroupPool implements IO<IOBridge, IOBridge>
{

	protected final String id;

	protected Map<UUID, Group> groups = new HashMap<>();

	protected List<IGroupPoolListener<?>> listeners = new ArrayList<>();

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

	public Collection<Group> getGroups()
	{
		return this.groups.values();
	}

	public void clear()
	{
		this.groups.clear();
	}

	protected List<IGroupHook> createHooks(Group group)
	{
		List<IGroupHook> list = new ArrayList<>(this.listeners.size());
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
		UtilModule.logger().debug("Adding group " + group.getName());
		this.groups.put(group.getUUID(), group);
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			listener.onGroupAdded(group);
		}
	}

	protected void removeGroupDirectly(Group group)
	{
		UtilModule.logger().debug("Removing group " + group.getName());
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

	/**
	 * @param group Group to change info for
	 * @param groupInfo New Groupinfo instance
	 */
	protected void changeGroupInfoDirectly(Group group, GroupInfo groupInfo)
	{
		UtilModule.logger().debug("Changing info of group " + group.getName());
		GroupInfo infoOld = group.getGroupInfo();
		group.setGroupInfo(groupInfo);
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			listener.onGroupInfoChanged(group, infoOld, groupInfo);
		}
	}

	protected void addMemberDirectly(Group group, GroupMember member)
	{
		this.assertHasMemberData(group);
		UtilModule.logger().debug("Adding member " + member.getProfile().getUsername() + " to group " + group.getName());
		member.joinGroup(group);
		group.getMemberData().join(member);
	}

	protected void removeMemberDirectly(Group group, GroupMember member)
	{
		this.assertHasMemberData(group);
		UtilModule.logger().debug("Removing member " + member.getProfile().getUsername() + " from group " + group.getName());
		member.leaveGroup(group);
		group.getMemberData().leave(member);
	}

	protected void inviteDirectly(Group group, GroupMember member, GroupMember inviter)
	{
		this.assertHasMemberData(group);
		UtilModule.logger().debug("Inviting member " + member.getProfile().getUsername() + " to group " + group.getName());
		group.getMemberData().invite(member);
		member.addInvite(group);
	}

	protected void removeInvitationDirectly(Group group, GroupMember member)
	{
		this.assertHasMemberData(group);
		UtilModule.logger().debug("Removing invitation of member " + member.getProfile().getUsername() + " from group " + group.getName());
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

	public abstract void changeGroupInfo(UUID changing, Group group, GroupInfo newInfo);

	protected boolean assertValidGroup(Group group)
	{
		if (!this.groups.containsKey(group.getUUID()))
		{
			UtilModule.logger().debug("Trying to manage group that is not in this pool: " + group.getName());
			return false;
		}
		return true;
	}

	protected boolean assertMemberIn(Group group, UUID player)
	{
		if (!group.hasMemberData() || !group.getMemberData().contains(player))
		{
			UtilModule.logger().debug("Member is not in the group like excepted. Group: " + group.getName() + " player: " + GroupModule.locate().getPlayers().get(player).getProfile().getUsername());
			return false;
		}
		return true;
	}

	public Group get(String name)
	{
		for (Group group : this.groups.values())
		{
			if (group.getName().equalsIgnoreCase(name))
			{
				return group;
			}
		}

		return null;
	}

	public Group get(UUID uuid)
	{
		return this.groups.get(uuid);
	}

	@Override
	public void read(IOBridge input)
	{
		List<Group> groups = IOUtil.getIOList("groups", input);
		for (Group group : groups)
		{
			this.groups.put(group.getUUID(), group);
		}
	}

	@Override
	public void write(IOBridge output)
	{
		IOUtil.setIOList("groups", new ArrayList<>(this.groups.values()), output);
	}

}
