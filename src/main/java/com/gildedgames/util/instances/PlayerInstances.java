package com.gildedgames.util.instances;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.player.GroupMember;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerInstances implements IPlayerHook
{
	private final IPlayerHookPool<PlayerInstances> pool;

	private final IPlayerProfile playerProfile;

	private Instance activeInstance;

	private BlockPosDimension outside;

	private Map<BlockPosDimension, Instance> instanceMap = new HashMap<BlockPosDimension, Instance>();

	public PlayerInstances(IPlayerHookPool<PlayerInstances> pool, IPlayerProfile profile)
	{
		this.pool = pool;
		this.playerProfile = profile;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTHelper.setBlockPosDimension(output, this.outside, "outside");
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.outside = NBTHelper.getBlockPosDimension(input, "outside");
	}

	public Instance instanceFor(BlockPosDimension pos)
	{
		GroupMember groupMember = GroupCore.getGroupMember(this.playerProfile.getEntity());
		return null;
	}

	@Override
	public IPlayerHookPool<PlayerInstances> getParentPool()
	{
		return this.pool;
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.playerProfile;
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public boolean onLivingAttack(DamageSource source)
	{
		return true;
	}

	@Override
	public void onDeath()
	{

	}

	@Override
	public void onChangedDimension()
	{

	}

	@Override
	public void onRespawn()
	{

	}

	@Override
	public boolean isDirty()
	{
		return false;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public void markClean()
	{
	}

	@Override
	public void syncTo(ByteBuf output, com.gildedgames.util.io_manager.io.IOSyncable.SyncSide to)
	{
	}

	@Override
	public void syncFrom(ByteBuf input, com.gildedgames.util.io_manager.io.IOSyncable.SyncSide from)
	{
	}

	public Instance getInstance()
	{
		return this.activeInstance;
	}

	protected void setInstance(Instance i)
	{
		this.activeInstance = i;
	}

	public BlockPosDimension outside()
	{
		return this.outside;
	}

	public void setOutside(BlockPosDimension pos)
	{
		this.outside = pos;
	}

}
