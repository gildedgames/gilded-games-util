package com.gildedgames.util.player;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

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

	public int getPoolID(IPlayerHookPool<?> playerHookPool)
	{
		//I'm doubting the validity of this method. Consider the case where the client has a mod
		//that uses PlayerHooks that the server doesn't have. It is possible that the server will 
		//choose the wrong id then, breaking syncing.
		//Using the name for syncing sounds more reliable
		for (int id = 0; id < this.getPools().size(); id++)
		{
			IPlayerHookPool<?> pool = this.getPools().get(id);

			if (pool == playerHookPool)
			{
				return id;
			}
		}

		return -1;
	}

	public void writeHookReference(IPlayerHook playerHook, ByteBuf buf)
	{
		int poolID = this.getPoolID(playerHook.getParentPool());//PlayerCore.locate().getPoolID(playerHook.getParentPool());

		buf.writeInt(poolID);

		playerHook.getProfile().writeToClient(buf);
	}

	public IPlayerHook readHookReference(EntityPlayer player, ByteBuf buf)
	{
		IPlayerHookPool<?> manager = this.getPools().get(buf.readInt());//PlayerCore.locate().getPools().get(buf.readInt());

		PlayerProfile profile = new PlayerProfile();

		profile.readFromServer(buf);//Assuming disregard cuz player is known

		IPlayerHook playerHook = manager.get(player);

		return playerHook;
	}

	public IPlayerHook readHookReference(Side side, ByteBuf buf)
	{
		IPlayerHookPool<?> manager = this.getPools().get(buf.readInt());//PlayerCore.locate().getPools().get(buf.readInt());

		PlayerProfile profile = new PlayerProfile();

		profile.readFromServer(buf);

		IPlayerHook playerHook = manager.get(profile.getUUID());

		return playerHook;
	}

	public void registerPlayerHookPool(IPlayerHookPool<?> playerPool)
	{
		this.getPools().add(playerPool);
	}

}
