package com.gildedgames.util.modules.group.common.permissions;

import java.util.Random;
import java.util.UUID;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.player.GroupMember;
import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.io_manager.util.IOUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class GroupPermsDefault implements IGroupPerms
{
	public enum PermissionType
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

	private UUID owner;

	private String ownerUsername;

	private PermissionType type = PermissionType.OPEN;

	private GroupPermsDefault()
	{

	}

	public GroupPermsDefault(GroupMember creating, PermissionType type)
	{
		this.owner = creating.getPlayer().getUniqueID();
		this.ownerUsername = creating.getPlayer().getName();
		this.type = type;
	}

	@Override
	public void write(IOBridge output)
	{
		output.setString("permtype", this.type.name());
		IOUtil.setUUID(this.owner, output, "owner");
		output.setString("ownerUsername", this.ownerUsername);
	}

	@Override
	public void read(IOBridge input)
	{
		this.type = PermissionType.valueOf(input.getString("permtype"));
		this.owner = IOUtil.getUUID(input, "owner");
		this.ownerUsername = input.getString("ownerUsername");
	}

	@Override
	public void onMemberRemoved(Group group, UUID member)
	{
		if (member.equals(this.owner) && group.getMemberData().size() > 0)
		{
			Random random = new Random();
			this.owner = group.getMemberData().getMembers().get(random.nextInt(group.getMemberData().size()));

			this.ownerUsername = GroupMember.get(this.owner).getPlayer().getName();
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
		return this.type.canEveryoneInvite() || inviter.getPlayer().getUniqueID().equals(this.owner);
	}

	@Override
	public boolean canChangeOwner(Group group, GroupMember newOwner, GroupMember changing)
	{
		return changing.getPlayer().getUniqueID().equals(this.owner);
	}

	@Override
	public boolean canJoin(Group group, UUID member)
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
		return remover.getPlayer().getUniqueID().equals(this.owner);
	}

	@Override
	public boolean canRemoveMember(Group group, GroupMember toRemove, GroupMember remover)
	{
		return remover.equals(toRemove) || remover.getPlayer().getUniqueID().equals(this.owner);
	}

	@Override
	public UUID owner()
	{
		return this.owner;
	}

	public String ownerUsername()
	{
		GroupMember member = GroupMember.get(this.owner);
//		if (member.getProfile().isLoggedIn())
//		{
			this.ownerUsername = member.getPlayer().getName();
//		}
		return this.ownerUsername;
	}

	@Override
	public boolean canEditGroupInfo(Group group, UUID editing)
	{
		return editing.equals(this.owner);
	}

	@Override
	public boolean equals(Object arg0)
	{
		GroupPermsDefault perms = ObjectFilter.cast(arg0, GroupPermsDefault.class);
		return perms != null && perms.type == this.type && perms.owner.equals(this.owner);
	}
}
