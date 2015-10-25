package com.gildedgames.util.group.common.core;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.common.IGroupPoolListener;
import com.gildedgames.util.group.common.IGroupPoolListenerClient;
import com.gildedgames.util.group.common.permissions.IGroupPerms;
import com.gildedgames.util.group.common.player.GroupMember;

import net.minecraft.client.Minecraft;
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
		GroupInfo groupInfo = new GroupInfo(name, perms);
		UtilCore.NETWORK.sendToServer(new PacketAddGroup(this, groupInfo));
		return null;
	}

	@Override
	public void addMember(EntityPlayer player, Group group)
	{
		if (!super.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilCore.NETWORK.sendToServer(new PacketAddMember(this, group, member));
	}

	@Override
	public void removeMember(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilCore.NETWORK.sendToServer(new PacketRemoveMember(this, group, member));
	}

	@Override
	public void remove(Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}
		UtilCore.NETWORK.sendToServer(new PacketRemoveGroup(this, group));
	}

	@Override
	public void invite(EntityPlayer player, EntityPlayer inviting, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}

		if (inviting.equals(Minecraft.getMinecraft().thePlayer))
		{
			UtilCore.print("Tried to invite as a different player!");
			return;
		}
		GroupMember member = GroupMember.get(player);
		UtilCore.NETWORK.sendToServer(new PacketAddInvite(this, group, member, this.thePlayer()));
	}

	@Override
	public void removeInvitation(EntityPlayer player, Group group)
	{
		if (!this.assertValidGroup(group))
		{
			return;
		}

		if (!group.getMemberData().isInvited(player))
		{
			UtilCore.print("Tried to remove an invitation of someone who wasn't invited.");
		}
		GroupMember member = GroupMember.get(player);
		UtilCore.NETWORK.sendToServer(new PacketRemoveInvite(this, group, member));
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
		UtilCore.debugPrint("Joined group " + group.getName());
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
		UtilCore.debugPrint("Received invite for group " + group.getName());
		this.thePlayer().addInvite(group);
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onInvited(group, inviter.getProfile().getEntity());
		}
	}

	protected void invitationRemoved(Group group)
	{
		UtilCore.debugPrint("Invitation for group " + group.getName() + " removed.");
		this.thePlayer().removeInvite(group);
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onInviteRemoved(group);
		}
	}

	private void onLeave(Group group)
	{
		UtilCore.debugPrint("Left the group " + group.getName());
		for (IGroupPoolListenerClient<?> listener : this.getClientListeners())
		{
			listener.onLeave(group);
		}
		group.setMemberData(null);
	}

	private List<IGroupPoolListenerClient<?>> getClientListeners()
	{
		List<IGroupPoolListenerClient<?>> listeners = new ArrayList<IGroupPoolListenerClient<?>>();
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
		return GroupMember.get(Minecraft.getMinecraft().thePlayer);
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
			UtilCore.print("Client trying to modify group he's not in");
			return false;
		}
		return true;
	}

}
