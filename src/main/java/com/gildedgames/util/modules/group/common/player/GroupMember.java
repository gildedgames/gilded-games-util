package com.gildedgames.util.modules.group.common.player;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import com.gildedgames.util.modules.entityhook.impl.providers.PlayerHookProvider;
import com.gildedgames.util.modules.group.GroupModule;
import com.gildedgames.util.modules.group.common.core.Group;
import com.gildedgames.util.modules.group.common.core.GroupPool;
import com.gildedgames.util.modules.group.common.core.PacketAddInvite;
import com.gildedgames.util.modules.group.common.core.PacketGroupPool;
import com.gildedgames.util.modules.group.common.core.PacketJoin;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupMember extends EntityHook<EntityPlayer>
{
	public static final PlayerHookProvider<GroupMember> PROVIDER = new PlayerHookProvider<>("util:groups", new GroupMember.Factory());

	private List<Group> groups = new ArrayList<>();

	private List<Group> invitations = new ArrayList<>();

	public static GroupMember get(EntityPlayer player)
	{
		return GroupMember.PROVIDER.getHook(player);
	}

	public static GroupMember get(UUID uuid)
	{
		return GroupMember.PROVIDER.getHook(uuid);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void init(Entity entity, World world)
	{
		super.init(entity, world);

		if (player.worldObj.isRemote)
		{
			this.groups.clear();
			this.invitations.clear();

			return;
		}

		for (GroupPool pool : GroupModule.locate().getPools())
		{
			UtilModule.NETWORK.sendTo(new PacketGroupPool(pool), (EntityPlayerMP) this.getEntity());

			UUID uuid = this.getUniqueId();

			for (Group group : pool.getGroups())
			{
				if (group.getMemberData().contains(uuid))
				{
					UtilModule.NETWORK.sendTo(new PacketJoin(group.getParentPool(), group), (EntityPlayerMP) this.getEntity());
				}
				else if (group.getMemberData().isInvited(uuid))
				{
					UtilModule.NETWORK.sendTo(new PacketAddInvite(group.getParentPool(), group, this, this), (EntityPlayerMP) this.getEntity());
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

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{

	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{

	}

	public static class Factory implements IEntityHookFactory<GroupMember>
	{
		@Override
		public GroupMember createHook()
		{
			return new GroupMember();
		}

		@Override
		public void writeFull(ByteBuf buf, GroupMember hook) { }

		@Override
		public void readFull(ByteBuf buf, GroupMember hook) { }
	}
}
