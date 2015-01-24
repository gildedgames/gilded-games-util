package com.gildedgames.util.player.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.PlayerProfile;

import cpw.mods.fml.relauncher.Side;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class PlayerHookPool<T extends IPlayerHook> implements IPlayerHookPool<T>
{

	private HashMap<UUID, T> playerMap = new HashMap<UUID, T>();

	private ArrayList<UUID> sentRequests = new ArrayList<UUID>();

	private Class<T> type;

	private final String name;

	private final IPlayerHookFactory<T> factory;

	private final Side side;

	public PlayerHookPool(String name, IPlayerHookFactory<T> factory, Side side)
	{
		this.name = name;
		this.factory = factory;
		this.side = side;
	}

	@Override
	public void clear()
	{
		this.playerMap = new HashMap<UUID, T>();
		this.sentRequests = new ArrayList<UUID>();
	}

	@Override
	public T get(EntityPlayer player)
	{
		return this.get(player.getUniqueID());
	}

	@Override
	public IPlayerHookFactory<T> getFactory()
	{
		return this.factory;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void add(T player)
	{
		this.playerMap.put(player.getProfile().getUUID(), player);
	}

	@Override
	public T get(UUID uuid)
	{
		T player = this.playerMap.get(uuid);

		if (player == null)
		{
			if (this.side.isClient())
			{
				if (!this.sentRequests.contains(uuid))
				{
					UtilCore.NETWORK.sendToServer(new MessagePlayerHookRequest(this, uuid));
					this.sentRequests.add(uuid);
				}
			}

			PlayerProfile profile = new PlayerProfile();
			profile.setUUID(uuid);

			player = this.factory.create(profile, this);

			this.add(player);
		}

		//Old code. PlayerHooks should never be instantiated without a profile
		/*if (player.getProfile() == null)
>>>>>>> 1e9f0d8c6ae71a52bbe265e19bebb471342c9a15:src/main/java/com/gildedgames/util/player/common/PlayerHookPool.java
		{
			PlayerProfile profile = new PlayerProfile();
			profile.setUUID(uuid);

			player.setProfile(profile);
		}*/

		return player;
	}

	@Override
	public void setPlayerHooks(List<T> players)
	{
		this.clear();
		for (T player : players)
		{
			this.add(player);
		}
	}

	@Override
	public Collection<T> getPlayerHooks()
	{
		return this.playerMap.values();
	}

	@Override
	public boolean shouldSave()
	{
		return true;
	}

}
