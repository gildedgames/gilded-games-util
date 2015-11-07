package com.gildedgames.util.group.common.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.util.IOUtil;

import net.minecraft.entity.player.EntityPlayer;

/**
 * The Iterator is over the online members only.
 * @author Emile
 *
 */
public class MemberData implements Iterable<GroupMember>
{
	private final List<UUID> members = new ArrayList<UUID>();

	private final List<UUID> invitedMembers = new ArrayList<UUID>();

	private List<IGroupHook> hooks = new ArrayList<IGroupHook>();

	protected void setHooks(List<IGroupHook> hooks)
	{
		this.hooks = hooks;
	}

	public void write(IOBridge output)
	{
		int memberSize = this.members.size();
		output.setInteger("memberSize", memberSize);
		for (int i = 0; i < memberSize; i++)
		{
			IOUtil.setUUID(this.members.get(i), output, "member" + i);
		}

		int invitedSize = this.invitedMembers.size();
		output.setInteger("inviteSize", invitedSize);
		for (int i = 0; i < invitedSize; i++)
		{
			IOUtil.setUUID(this.invitedMembers.get(i), output, "invited" + i);
		}

		IOUtil.setIOList("hooks", this.hooks, output);
	}

	public void read(IOBridge input)
	{
		this.members.clear();
		this.invitedMembers.clear();

		int memberSize = input.getInteger("memberSize");
		for (int i = 0; i < memberSize; i++)
		{
			this.members.add(IOUtil.getUUID(input, "member" + i));
		}

		int invitedSize = input.getInteger("inviteSize");
		for (int i = 0; i < invitedSize; i++)
		{
			this.invitedMembers.add(IOUtil.getUUID(input, "invited" + i));
		}

		this.hooks = IOUtil.getIOList("hooks", input);
	}

	protected void join(GroupMember member)
	{
		this.invitedMembers.remove(member.getProfile().getUUID());

		if (this.members.contains(member.getProfile().getUUID()))
		{
			UtilCore.print("Tried to join group but player " + member.getProfile().getUsername() + " was already in it");
			return;
		}

		this.members.add(member.getProfile().getUUID());

		for (IGroupHook hook : this.hooks)
		{
			hook.onMemberAdded(member);
		}
	}

	protected void leave(GroupMember member)
	{
		if (this.assertMember(member))
		{
			this.members.remove(member.getProfile().getUUID());
			for (IGroupHook hook : this.hooks)
			{
				hook.onMemberRemoved(member);
			}
		}
	}

	protected void invite(GroupMember member)
	{
		if (this.members.contains(member.getProfile().getUUID()))
		{
			UtilCore.print("Tried to invite player who is already a member: " + member.getProfile().getUsername());
			return;
		}

		for (IGroupHook hook : this.hooks)
		{
			hook.onMemberInvited(member);
		}

		this.invitedMembers.add(member.getProfile().getUUID());
	}

	protected void removeInvitation(GroupMember member)
	{
		if (this.members.contains(member.getProfile().getUUID()))
		{
			UtilCore.print("Tried to remove invitation of a player who is already a member: " + member.getProfile().getUsername());
			return;
		}

		if (!this.invitedMembers.contains(member.getProfile().getUUID()))
		{
			UtilCore.print("Tried to remove invitation of a player who wasn't invited: " + member.getProfile().getUsername());
			return;
		}

		for (IGroupHook hook : this.hooks)
		{
			hook.onInviteRemoved(member);
		}

		this.invitedMembers.remove(member.getProfile().getUUID());
	}

	protected boolean assertMember(GroupMember member)
	{
		if (!this.members.contains(member.getProfile().getUUID()))
		{
			UtilCore.print("Trying to do something with a player who is not a member");
			return false;
		}
		return true;
	}

	protected void talkTo()
	{
		//TODO
	}

	@Override
	public Iterator<GroupMember> iterator()
	{
		return this.onlineMembers().iterator();
	}

	public boolean contains(UUID uuid)
	{
		return this.members.contains(uuid);
	}

	public int size()
	{
		return this.members.size();
	}

	public List<GroupMember> onlineMembers()
	{
		List<GroupMember> onlineMembers = new ArrayList<GroupMember>();
		for (UUID uuid : this.members)
		{
			GroupMember member = GroupCore.locate().getPlayers().get(uuid);
			if (member.getProfile().getEntity() != null && member.getProfile().isLoggedIn())
			{
				onlineMembers.add(member);
			}
		}
		return onlineMembers;
	}

	public List<UUID> getMembers()
	{
		return this.members;
	}

	public List<UUID> getInvitations()
	{
		return this.invitedMembers;
	}

	public boolean isInvited(EntityPlayer player)
	{
		return this.isInvited(player.getGameProfile().getId());
	}

	public boolean isInvited(UUID uuid)
	{
		return !this.members.contains(uuid) && this.invitedMembers.contains(uuid);
	}
}
