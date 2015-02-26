package com.gildedgames.util.player;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.PlayerProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

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

		playerHook.getProfile().writeToClient(buf);
	}

	public IPlayerHook readHookReference(EntityPlayer player, ByteBuf buf)
	{
		IPlayerHookPool<?> manager = this.getPool(ByteBufUtils.readUTF8String(buf));

		PlayerProfile profile = new PlayerProfile();

		profile.readFromServer(buf);//Assuming disregard cuz player is known

		return manager.get(player);
	}

	public IPlayerHook readHookReference(Side side, ByteBuf buf)
	{
		IPlayerHookPool<?> manager = this.getPool(ByteBufUtils.readUTF8String(buf));

		PlayerProfile profile = new PlayerProfile();

		profile.readFromServer(buf);

		return manager.get(profile.getUUID());
	}

	public void registerPlayerHookPool(IPlayerHookPool<?> playerPool)
	{
		this.getPools().add(playerPool);
	}

}
