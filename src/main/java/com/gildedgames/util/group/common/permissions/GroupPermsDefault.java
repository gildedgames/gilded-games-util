package com.gildedgames.util.group.common.permissions;

import java.util.Random;

import com.gildedgames.util.group.common.core.Group;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.util.IOUtil;

import net.minecraft.entity.player.EntityPlayer;

public class GroupPermsDefault implements IGroupPerms
{
	public static enum PermissionType
	{
		OPEN
		{
			@Override
			protected boolean isVisible()
			{
				return true;
			}

			@Override
			protected boolean requiresInvite()
			{
				return false;
			}

			@Override
			public String getName()
			{
				return "group.open.name";
			}

			@Override
			public String getDescription()
			{
				return "group.open.description";
			}

			@Override
			protected boolean canEveryoneInvite()
			{
				return true;
			}
		},
		CLOSED
		{
			@Override
			protected boolean isVisible()
			{
				return true;
			}

			@Override
			protected boolean requiresInvite()
			{
				return true;
			}

			@Override
			public String getName()
			{
				return "group.closed.name";
			}

			@Override
			public String getDescription()
			{
				return "group.closed.description";
			}

			@Override
			protected boolean canEveryoneInvite()
			{
				return false;
			}
		},
		PRIVATE
		{
			@Override
			protected boolean isVisible()
			{
				return false;
			}

			@Override
			protected boolean requiresInvite()
			{
				return true;
			}

			@Override
			public String getName()
			{
				return "group.private.name";
			}

			@Override
			public String getDescription()
			{
				return "group.private.description";
			}

			@Override
			protected boolean canEveryoneInvite()
			{
				return false;
			}
		};
		protected abstract boolean isVisible();

		protected abstract boolean requiresInvite();

		protected abstract boolean canEveryoneInvite();

		public abstract String getName();

		public abstract String getDescription();

	}

	private GroupMember owner;

	private PermissionType type = PermissionType.OPEN;

	private GroupPermsDefault()
	{

	}

	public GroupPermsDefault(GroupMember creating, PermissionType type)
	{
		this.owner = creating;
		this.type = type;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setString("permtype", this.type.name());
		IOUtil.setUUID(this.owner.getProfile().getUUID(), output, "owner");
	}

	@Override
	public void read(IOBridge input)
	{
		this.type = PermissionType.valueOf(input.getString("permtype"));
		this.owner = GroupMember.get(IOUtil.getUUID(input, "owner"));
	}

	@Override
	public void onMemberRemoved(Group group, GroupMember member)
	{
		if (member.equals(this.owner) && group.getMemberData().size() > 0)
		{
			Random random = member.getProfile().getEntity().getRNG();
			this.owner = group.getMemberData().getMembers().get(random.nextInt(group.getMemberData().size()));
			//This isn't synced to the clients!
		}
	}

	@Override
	public String getName()
	{
		return this.type.getName();
	}

	@Override
	public String getDescription()
	{
		return this.type.getDescription();
	}

	@Override
	public boolean canInvite(Group group, GroupMember member, GroupMember inviter)
	{
		return this.type.canEveryoneInvite() || inviter.equals(this.owner);
	}

	@Override
	public boolean canChangeOwner(Group group, GroupMember newOwner, GroupMember changing)
	{
		return changing.equals(this.owner);
	}

	@Override
	public boolean canJoin(Group group, GroupMember member)
	{
		return !this.type.requiresInvite() || group.hasMemberData() && group.getMemberData().contains(member);
	}

	@Override
	public boolean isVisible(Group group)
	{
		return this.type.isVisible();
	}

	@Override
	public boolean canRemoveGroup(Group group, GroupMember remover)
	{
		return remover.equals(this.owner);
	}

	@Override
	public boolean canRemoveMember(Group group, GroupMember toRemove, GroupMember remover)
	{
		return remover.equals(this.owner);
	}

	public EntityPlayer getOwner()
	{
		return this.owner.getProfile().getEntity();
	}

	@Override
	public GroupMember owner()
	{
		return this.owner;
	}

}
