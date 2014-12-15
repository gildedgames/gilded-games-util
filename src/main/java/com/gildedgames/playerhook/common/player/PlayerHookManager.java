package com.gildedgames.playerhook.common.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerInfoRequest;

public class PlayerHookManager
{
	
	private static PlayerHookManager clientHandler = new PlayerHookManager();

	private static PlayerHookManager serverHandler = new PlayerHookManager();

	private HashMap<String, PlayerHook> playerMap = new HashMap<String, PlayerHook>();

	private ArrayList<String> sentRequests = new ArrayList<String>();

	public static void clear()
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();

		if (side.isClient())
		{
			clientHandler = new PlayerHookManager();
		}
		else
		{
			serverHandler = new PlayerHookManager();
		}
	}
	
	public static PlayerHookManager getClientInstance()
	{
		return clientHandler;
	}
	
	public static PlayerHookManager getServerInstance()
	{
		return serverHandler;
	}

	public static PlayerHookManager instance(Side side)
	{
		return side.isClient() ? getClientInstance() : getServerInstance();
	}

	public void addPlayer(PlayerHook player)
	{
		this.playerMap.put(player.getUsername(), player);
	}

	public PlayerHook getPlayerHook(String username)
	{
		PlayerHook player = this.playerMap.get(username);

		if (player == null)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();

			if (side.isClient())
			{
				if (!this.sentRequests.contains(username))
				{
					PlayerHookCore.NETWORK.sendToServer(new MessagePlayerInfoRequest(username));
					this.sentRequests.add(username);
				}
			}

			player = new PlayerHook(username);
			this.addPlayer(player);
		}

		return player;
	}

	public void setPlayers(ArrayList<PlayerHook> players)
	{
		for (PlayerHook player : players)
		{
			this.addPlayer(player);
		}
	}

	public Collection<PlayerHook> getPlayers()
	{
		return this.playerMap.values();
	}

}
