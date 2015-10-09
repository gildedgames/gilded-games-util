package com.gildedgames.util.group.common.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.core.GroupPool;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GroupMember implements IPlayerHook
{

	private final IPlayerProfile profile;

	private final IPlayerHookPool<GroupMember> pool;

	private boolean isDirty;

	private List<Group> groups = new ArrayList<Group>();

	private List<Group> invitations = new ArrayList<Group>();

	public GroupMember(IPlayerProfile profile, IPlayerHookPool<GroupMember> pool)
	{
		this.profile = profile;
		this.pool = pool;
	}

	public static GroupMember get(EntityPlayer player)
	{
		return GroupCore.locate().getPlayers().get(player);
	}

	public static GroupMember get(UUID uuid)
	{
		EntityPlayer entity = UtilCore.getPlayerOnServerFromUUID(uuid);

		return GroupMember.get(entity);
	}

	@Override
	public void read(NBTTagCompound arg0)
	{

	}

	@Override
	public void write(NBTTagCompound arg0)
	{

	}

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public void entityInit(EntityPlayer player)
	{

	}

	@Override
	public IPlayerHookPool<GroupMember> getParentPool()
	{
		return this.pool;
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.profile;
	}

	@Override
	public void syncTo(ByteBuf output, SyncSide to)
	{

	}

	@Override
	public void syncFrom(ByteBuf input, SyncSide from)
	{

	}

	public void joinGroup(Group group)
	{
		this.removeInvite(group);
		this.groups.add(group);
	}

	public void leaveGroup(Group group)
	{
		this.groups.remove(group);
	}

	public List<Group> getGroupsIn()
	{
		return this.groups;
	}

	public List<Group> groupsInFor(GroupPool pool)
	{
		List<Group> groups = new ArrayList<Group>();
		for (Group group : this.groups)
		{
			if (group.getParentPool().equals(pool))
			{
				groups.add(group);
			}
		}
		return groups;
	}

	public void addInvite(Group group)
	{
		this.invitations.add(group);
	}

	public void removeInvite(Group group)
	{
		while (this.invitations.remove(group))
		{
		}
	}

	public boolean isInvitedFor(Group group)
	{
		return this.invitations.contains(group);
	}

}
