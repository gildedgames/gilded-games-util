package com.gildedgames.util.group.common.util;

import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.IGroup;
import com.gildedgames.util.group.common.IGroupPerms;
import com.gildedgames.util.group.common.IGroupPool;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.IOCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class Group implements IGroup
{
	
	protected final IGroupPool parentPool;
	
	protected IGroupPerms permissions;
	
	protected String name;
	
	protected GroupMember owner;
	
	protected final List<GroupMember> members = new ArrayList<GroupMember>();
	
	protected final List<GroupMember> invitedMembers = new ArrayList<GroupMember>();
	
	public Group(IGroupPool parentPool, IGroupPerms permissions)
	{
		this.parentPool = parentPool;
		this.setPermissions(permissions);
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTFactory factory = new NBTFactory();
		
		IOCore.io().set("permissions", output, factory, this.permissions);
		output.setString("name", this.name);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTFactory factory = new NBTFactory();
		
		this.permissions = IOCore.io().get("permissions", input, factory);
		this.name = input.getString("name");
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public IGroupPerms getPermissions()
	{
		return this.permissions;
	}

	@Override
	public void setPermissions(IGroupPerms permissions)
	{
		this.permissions = permissions;
	}

	@Override
	public boolean join(EntityPlayer player)
	{
		GroupMember member = GroupCore.getGroupMember(player);
		
		return this.addMember(member);
	}

	@Override
	public boolean leave(EntityPlayer player)
	{
		GroupMember member = GroupCore.getGroupMember(player);
		
		return this.removeMember(member);
	}

	@Override
	public boolean invite(EntityPlayer player)
	{
		GroupMember member = GroupCore.getGroupMember(player);
		
		return this.inviteMember(member);
	}

	@Override
	public GroupMember getOwner()
	{
		return this.owner;
	}
	
	private boolean addMember(GroupMember member)
	{
		return this.members.add(member);
	}
	
	private boolean removeMember(GroupMember member)
	{
		return this.members.remove(member);
	}
	
	private boolean inviteMember(GroupMember member)
	{
		return this.invitedMembers.add(member);
	}

	@Override
	public List<GroupMember> getMembers()
	{
		return new ArrayList<GroupMember>(this.members);
	}

	@Override
	public List<GroupMember> getMembersOnline()
	{
		ArrayList<GroupMember> onlineMembers = new ArrayList<GroupMember>();
		
		for (GroupMember member : this.members)
		{
			if (member != null && member.getProfile().isLoggedIn())
			{
				onlineMembers.add(member);
			}
		}
		
		return onlineMembers;
	}

	@Override
	public List<GroupMember> getMembersOffline()
	{
		ArrayList<GroupMember> offlineMembers = new ArrayList<GroupMember>();
		
		for (GroupMember member : this.members)
		{
			if (member != null && !member.getProfile().isLoggedIn())
			{
				offlineMembers.add(member);
			}
		}
		
		return offlineMembers;
	}

	@Override
	public IGroupPool getParentPool()
	{
		return this.parentPool;
	}

}
