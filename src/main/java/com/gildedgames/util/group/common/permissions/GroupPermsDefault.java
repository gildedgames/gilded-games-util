package com.gildedgames.util.group.common.permissions;

import java.util.Random;

import com.gildedgames.util.core.UtilCore;
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
			protected String getName()
			{
				return "group.open.name";
			}

			@Override
			protected String getDescription()
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
			protected String getName()
			{
				return "group.closed.name";
			}

			@Override
			protected String getDescription()
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
			protected String getName()
			{
				return "group.private.name";
			}

			@Override
			protected String getDescription()
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

		protected abstract String getName();

		protected abstract String getDescription();

	}

	private GroupMember owner;

	private PermissionType type = PermissionType.OPEN;

	private final Group group;

	public GroupPermsDefault(Group group, GroupMember creating)
	{
		this.group = group;
		this.owner = creating;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setString("permtype", this.type.getName());
		IOUtil.setUUID(this.owner.getProfile().getUUID(), output, "owner");
	}

	@Override
	public void read(IOBridge input)
	{
		this.type = PermissionType.valueOf(input.getString("permtype"));
		this.owner = GroupMember.get(UtilCore.getPlayerOnServerFromUUID(IOUtil.getUUID(input, "owner")));
	}

	@Override
	public void onMemberAdded(GroupMember member)
	{
	}

	@Override
	public void onMemberRemoved(GroupMember member)
	{
		if (member.equals(this.owner))
		{
			Random random = member.getProfile().getEntity().getRNG();
			this.owner = this.group.getMemberData().getMembers().get(random.nextInt(this.group.getMemberData().size()));
		}
	}

	@Override
	public void onMemberInvited(GroupMember member)
	{
	}

	@Override
	public void onInviteRemoved(GroupMember member)
	{
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
	public boolean canInvite(GroupMember member, GroupMember inviter)
	{
		return this.type.canEveryoneInvite() || inviter.equals(this.owner);
	}

	@Override
	public boolean canChangeOwner(GroupMember newOwner, GroupMember changing)
	{
		return changing.equals(this.owner);
	}

	@Override
	public boolean canJoin(GroupMember member)
	{
		return !this.type.requiresInvite() || this.group.hasMemberData() && this.group.getMemberData().contains(member);
	}

	@Override
	public boolean isVisible()
	{
		return this.type.isVisible();
	}

	@Override
	public boolean canRemoveGroup(GroupMember remover)
	{
		return remover.equals(this.owner);
	}

	@Override
	public boolean canRemoveMember(GroupMember toRemove, GroupMember remover)
	{
		return remover.equals(this.owner);
	}

	public EntityPlayer getOwner()
	{
		return this.owner.getProfile().getEntity();
	}

}
