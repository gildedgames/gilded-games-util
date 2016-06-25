package com.gildedgames.util.modules.group.common.player;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.core.GroupPool;
import com.gildedgames.util.modules.group.common.core.PacketAddInvite;
import com.gildedgames.util.modules.group.common.core.PacketGroupPool;
import com.gildedgames.util.modules.group.common.core.PacketJoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupMember implements IGroupMember
{
	private List<Group> groups = new ArrayList<>();

	private List<Group> invitations = new ArrayList<>();

	private EntityPlayer player;

	public GroupMember(EntityPlayer player)
	{
		this.player = player;
	}

	public static GroupMember get(EntityPlayer player)
	{
		return player.getCapability(GroupModule.GROUP_MEMBER, null);
	}

	public static GroupMember get(UUID uuid)
	{
		EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);

		if (player == null)
		{
			return null;
		}

		return GroupMember.get(player);
	}

	public void onLoaded()
	{
		if (this.player.worldObj.isRemote)
		{
			this.groups.clear();
			this.invitations.clear();

			return;
		}

		for (GroupPool pool : GroupModule.locate().getPools())
		{
			UtilModule.NETWORK.sendTo(new PacketGroupPool(pool), (EntityPlayerMP) this.player);

			UUID uuid = this.player.getUniqueID();

			for (Group group : pool.getGroups())
			{
				if (group.getMemberData().contains(uuid))
				{
					UtilModule.NETWORK.sendTo(new PacketJoin(group.getParentPool(), group), (EntityPlayerMP) this.player);
				}
				else if (group.getMemberData().isInvited(uuid))
				{
					UtilModule.NETWORK.sendTo(new PacketAddInvite(group.getParentPool(), group, this, this), (EntityPlayerMP) this.player);
				}
			}
		}
	}

	public void joinGroup(Group group)
	{
		this.removeInvite(group);
		if (!this.groups.contains(group))
		{
			this.groups.add(group);
		}
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
		List<Group> groups = new ArrayList<>();
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
		if (!this.invitations.contains(group))
		{
			this.invitations.add(group);
		}
	}

	public void removeInvite(Group group)
	{
		this.invitations.clear();
	}

	public boolean isInvitedFor(Group group)
	{
		return this.invitations.contains(group);
	}

	public EntityPlayer getPlayer()
	{
		return this.player;
	}

	public static class Storage implements Capability.IStorage<GroupMember>
	{
		@Override
		public NBTBase writeNBT(Capability<GroupMember> capability, GroupMember instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<GroupMember> capability, GroupMember instance, EnumFacing side, NBTBase nbt) { }
	}
}
