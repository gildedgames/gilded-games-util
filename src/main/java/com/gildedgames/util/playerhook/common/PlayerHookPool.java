package com.gildedgames.util.playerhook.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.playerhook.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;
import com.gildedgames.util.playerhook.common.player.PlayerProfile;

public class PlayerHookPool<T extends IPlayerHook> implements IPlayerHookPool<T>
{

	private HashMap<UUID, T> playerMap = new HashMap<UUID, T>();

	private ArrayList<UUID> sentRequests = new ArrayList<UUID>();

	private String name;
	
	private Class<T> type;

	public PlayerHookPool(String name, Class<T> playerHookType)
	{
		this.name = name;
		this.type = playerHookType;
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
		boolean isRemote = player.worldObj.isRemote;
		
		Side side = isRemote ? Side.CLIENT : Side.SERVER;
		
		return this.get(player.getUniqueID());
	}
	
	@Override
	public Class<T> getPlayerHookType()
	{
		return this.type;
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
			Side side = FMLCommonHandler.instance().getEffectiveSide();

			if (side.isClient())
			{
				if (!this.sentRequests.contains(uuid))
				{
					UtilCore.NETWORK.sendToServer(new MessagePlayerHookRequest(this, uuid));
					this.sentRequests.add(uuid);
				}
			}

			try
			{
				player = this.type.newInstance();

				PlayerProfile profile = new PlayerProfile();
				profile.setUUID(uuid);
				
				player.setProfile(profile);

				this.add(player);
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		
		if (player.getProfile() == null)
		{
			PlayerProfile profile = new PlayerProfile();
			profile.setUUID(uuid);
			
			player.setProfile(profile);
		}

		return player;
	}

	@Override
	public void setPlayerHooks(List<T> players)
	{
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
