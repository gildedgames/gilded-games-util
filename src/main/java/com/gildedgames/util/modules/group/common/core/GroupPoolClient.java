package com.gildedgames.util.modules.group.common.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.group.common.IGroupPoolListener;
import com.gildedgames.util.modules.group.common.IGroupPoolListenerClient;
import com.gildedgames.util.modules.group.common.permissions.IGroupPerms;
import com.gildedgames.util.modules.group.common.player.GroupMember;

import net.minecraft.entity.player.EntityPlayer;

//Basically just asks the server to do his work for him :) :) :)
//Won't ever change its state on its own to ensure clients not acting weirdly
public class GroupPoolClient extends GroupPool
{

	public GroupPoolClient(String id)
	{
		super(id);
	}

	@Override
	public Group create(String name, EntityPlayer creating, IGroupPerms perms)
	{
		GroupInfo groupInfo = new GroupInfo(UUID.randomUUID(), name, perms);
		UtilModule.NETWORK.sendToServer(new PacketAddGroup(this, groupInfo));
		return null;
	}

	@Override
	public void addMember(UUID player, Group group)
	{
		if (!super.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilModule.NETWORK.sendToServer(new PacketAddMember(this, group, member));
	}

	@Override
	public void removeMember(UUID player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilModule.NETWORK.sendToServer(new PacketRemoveMember(this, group, member));
	}

	@Override
	public void remove(Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		UtilModule.NETWORK.sendToServer(new PacketRemoveGroup(this, group));
	}

	@Override
	public void invite(UUID player, UUID inviting, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}

		if (player.equals(UtilModule.proxy.getPlayer().getGameProfile().getId()))
		{
			UtilModule.logger().warn("Tried to invite client themselves a different player!");
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilModule.NETWORK.sendToServer(new PacketAddInvite(this, group, member, this.thePlayer()));
	}

	@Override
	public void removeInvitation(UUID player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}

		if (!group.getMemberData().isInvited(player))
		{
			UtilModule.logger().warn("Tried to remove an invitation of someone who wasn't invited.");
		}
		GroupMember member = GroupMember.get(player);
		UtilModule.NETWORK.sendToServer(new PacketRemoveInvite(this, group, member));
	}

	@Override
	public void changeGroupInfo(UUID changing, Group group, GroupInfo newInfo)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		UtilModule.NETWORK.sendToServer(new PacketChangeGroupInfo(changing, group, newInfo));
	}

	@Override
	protected void removeMemberDirectly(Group group, GroupMember member)
	{
		super.removeMemberDirectly(group, member);
		if (member.equals(this.thePlayer()))
		{
			this.onLeave(group);
		}
	}

	protected void join(Group group, MemberData members)
	{
		UtilModule.logger().warn("Joined group " + group.getName());
		group.setMemberData(members);
		members.setHooks(this.createHooks(group));
		this.addMemberDirectly(group, this.thePlayer());
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onJoin(group);
		}
	}

	protected void inviteReceived(Group group, GroupMember inviter)
	{
		UtilModule.logger().warn("Received invite for group " + group.getName());
		this.thePlayer().addInvite(group);
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onInvited(group, inviter.getUniqueId());
		}
	}

	protected void invitationRemoved(Group group)
	{
		UtilModule.logger().warn("Invitation for group " + group.getName() + " removed.");
		this.thePlayer().removeInvite(group);
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onInviteRemoved(group);
		}
	}

	private void onLeave(Group group)
	{
		UtilModule.logger().warn("Left the group " + group.getName());
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onLeave(group);
		}
		group.setMemberData(null);
	}

	private List<IGroupPoolListenerClient<?>> getClientListeners()
	{
		List<IGroupPoolListenerClient<?>> listeners = new ArrayList<>();
		for (IGroupPoolListener<?> listener : this.listeners)
		{
			if (listener instanceof IGroupPoolListenerClient)
			{
				listeners.add((IGroupPoolListenerClient<?>) listener);
			}
		}
		return listeners;
	}

	private GroupMember thePlayer()
	{
		return GroupMember.get(UtilModule.proxy.getPlayer());
	}

	@Override
	protected boolean assertValidGroup(Group group)
	{
		if (!super.assertValidGroup(group))
		{
			return false;
		}
		if (!this.thePlayer().groupsInFor(this).contains(group))
		{
			UtilModule.logger().warn("Client trying to modify group he's not in");
			return false;
		}
		return true;
	}

}
