package com.gildedgames.util.group.common.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroupHook;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.util.IOUtil;

public class MemberData implements NBT, Iterable<GroupMember>
{
	private final List<GroupMember> members = new ArrayList<GroupMember>();

	private final List<GroupMember> invitedMembers = new ArrayList<GroupMember>();

	private List<IGroupHook> hooks = new ArrayList<IGroupHook>();

	protected void setHooks(List<IGroupHook> hooks)
	{
		this.hooks = hooks;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		int memberSize = this.members.size();
		output.setInteger("memberSize", memberSize);
		for (int i = 0; i < memberSize; i++)
		{
			IOUtil.setUUID(this.members.get(i).getProfile(), output, "member" + i);
		}

		int invitedSize = this.invitedMembers.size();
		output.setInteger("memberSize", invitedSize);
		for (int i = 0; i < invitedSize; i++)
		{
			IOUtil.setUUID(this.invitedMembers.get(i).getProfile(), output, "invited" + i);
		}

		IOUtil.setIOList("hooks", this.hooks, new NBTFactory(), output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.members.clear();
		this.invitedMembers.clear();

		int memberSize = input.getInteger("memberSize");
		for (int i = 0; i < memberSize; i++)
		{
			this.members.add(GroupCore.getGroupMember(IOUtil.getUUID(input, "member" + i)));
		}

		int invitedSize = input.getInteger("memberSize");
		for (int i = 0; i < invitedSize; i++)
		{
			this.members.add(GroupCore.getGroupMember(IOUtil.getUUID(input, "invited" + i)));
		}

		this.hooks = IOUtil.getIOList("hooks", new NBTFactory(), input);
	}

	protected void join(GroupMember member)
	{
		this.invitedMembers.remove(member);

		if (this.members.contains(member))
		{
			UtilCore.print("Tried to join group but player " + member.getProfile().getUsername() + " was already in it");
			return;
		}

		this.members.add(member);

		for (IGroupHook hook : this.hooks)
		{
			hook.onMemberAdded(member);
		}
	}

	protected void leave(GroupMember member)
	{
		if (this.assertMember(member))
		{
			this.members.remove(member);
			for (IGroupHook hook : this.hooks)
			{
				hook.onMemberRemoved(member);
			}
		}
	}

	protected void invite(GroupMember member)
	{
		if (this.members.contains(member))
		{
			UtilCore.print("Tried to invite player who is already a member: " + member.getProfile().getUsername());
			return;
		}

		for (IGroupHook hook : this.hooks)
		{
			hook.onMemberInvited(member);
		}

		this.invitedMembers.add(member);
	}

	protected boolean assertMember(GroupMember member)
	{
		if (!this.members.contains(member))
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
		return this.members.iterator();
	}

	public boolean contains(GroupMember groupMember)
	{
		return this.members.contains(groupMember);
	}

	public int size()
	{
		return this.members.size();
	}

	public List<GroupMember> getMembers()
	{
		return new ArrayList<GroupMember>(this.members);
	}

	public boolean isInvited(GroupMember player)
	{
		return !this.members.contains(player) && this.invitedMembers.contains(player);
	}
}
