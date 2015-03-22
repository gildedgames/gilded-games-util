package com.gildedgames.util.player;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.PlayerProfile;

public class PlayerServices
{

	private List<IPlayerHookPool<?>> playerHookPools;

	public List<IPlayerHookPool<?>> getPools()
	{
		if (this.playerHookPools == null)
		{
			this.playerHookPools = new ArrayList<IPlayerHookPool<?>>();
		}

		return this.playerHookPools;
	}

	public String getPoolID(IPlayerHookPool<?> playerHookPool)
	{
		return playerHookPool.getName();
	}

	public IPlayerHookPool<?> getPool(String id)
	{
		for (IPlayerHookPool<?> pool : this.getPools())
		{
			if (pool.getName().equals(id))
			{
				return pool;
			}
		}
		return null;
	}

	public void writeHookReference(IPlayerHook playerHook, ByteBuf buf)
	{
		String poolID = this.getPoolID(playerHook.getParentPool());//PlayerCore.locate().getPoolID(playerHook.getParentPool());

		ByteBufUtils.writeUTF8String(buf, poolID);

		playerHook.getProfile().syncTo(buf, SyncSide.CLIENT);
	}

	public IPlayerHook readHookReference(EntityPlayer player, ByteBuf buf)
	{
		IPlayerHookPool<?> manager = this.getPool(ByteBufUtils.readUTF8String(buf));

		PlayerProfile profile = new PlayerProfile();

		profile.syncFrom(buf, SyncSide.SERVER);//Assuming disregard cuz player is known

		return manager.get(player);
	}

	public IPlayerHook readHookReference(Side side, ByteBuf buf)
	{
		IPlayerHookPool<?> manager = this.getPool(ByteBufUtils.readUTF8String(buf));

		PlayerProfile profile = new PlayerProfile();

		profile.syncFrom(buf, SyncSide.SERVER);

		return manager.get(profile.getUUID());
	}

	public void registerPlayerHookPool(IPlayerHookPool<?> playerPool)
	{
		this.getPools().add(playerPool);
	}

}
