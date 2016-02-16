package com.gildedgames.util.player.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.player.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.PlayerProfile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerHookPool<T extends IPlayerHook> implements IPlayerHookPool<T>
{

	private Map<UUID, T> playerMap = new HashMap<>();

	private List<UUID> sentRequests = new ArrayList<>();

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
		this.playerMap = new HashMap<>();
		this.sentRequests = new ArrayList<>();
	}

	@Override
	public T get(EntityPlayer player)
	{
		if (player == null)
		{
			return null;
		}
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
					UtilModule.NETWORK.sendToServer(new MessagePlayerHookRequest(this, uuid));
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
