package com.gildedgames.util.playerhook;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;
import com.gildedgames.util.playerhook.common.player.PlayerProfile;

public class PlayerHookServices
{

	private List<IPlayerHookPool> playerHookPools;

	public PlayerHookServices()
	{

	}

	public List<IPlayerHookPool> getPools()
	{
		if (this.playerHookPools == null)
		{
			this.playerHookPools = new ArrayList<IPlayerHookPool>();
		}

		return this.playerHookPools;
	}

	public int getPoolID(IPlayerHookPool playerHookPool)
	{
		for (int id = 0; id < this.getPools().size(); id++)
		{
			IPlayerHookPool pool = this.getPools().get(id);

			if (pool == playerHookPool)
			{
				return id;
			}
		}

		return -1;
	}

	public void writeHookReference(IPlayerHook playerHook, ByteBuf buf)
	{
		int poolID = PlayerHookCore.locate().getPoolID(playerHook.getParentPool());

		buf.writeInt(poolID);

		playerHook.getProfile().writeToClient(buf);
	}

	public IPlayerHook readHookReference(EntityPlayer player, ByteBuf buf)
	{
		IPlayerHookPool manager = PlayerHookCore.locate().getPools().get(buf.readInt());

		PlayerProfile profile = new PlayerProfile();

		profile.readFromServer(buf);

		IPlayerHook playerHook = manager.get(player);

		return playerHook;
	}

	public IPlayerHook readHookReference(Side side, ByteBuf buf)
	{
		IPlayerHookPool manager = PlayerHookCore.locate().getPools().get(buf.readInt());

		PlayerProfile profile = new PlayerProfile();

		profile.readFromServer(buf);

		IPlayerHook playerHook = manager.get(profile.getUUID());

		return playerHook;
	}

	public void registerPlayerHookPool(IPlayerHookPool playerPool)
	{
		this.getPools().add(playerPool);
	}

}
