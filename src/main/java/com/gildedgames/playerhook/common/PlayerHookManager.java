package com.gildedgames.playerhook.common;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerHookRequest;
import com.gildedgames.playerhook.common.player.IPlayerHook;
import com.gildedgames.playerhook.common.player.PlayerProfile;

public class PlayerHookManager<T extends IPlayerHook>
{
	
	private static List<PlayerHookManager> managers = new ArrayList<PlayerHookManager>();
	
	private HashMap<UUID, T> playerMap = new HashMap<UUID, T>();

	private ArrayList<UUID> sentRequests = new ArrayList<UUID>();

	private String name;
	
	private Class<T> type;
	
	private int id;
	
	public PlayerHookManager(String name, Class<T> playerHookType)
	{
		this.name = name;
		this.type = playerHookType;
		this.id = PlayerHookManager.managers.size();
		
		PlayerHookCore.proxy.getManagers().add(this);
	}
	
	public void clear()
	{
		this.playerMap = new HashMap<UUID, T>();
		this.sentRequests = new ArrayList<UUID>();
	}

	public T get(EntityPlayer player)
	{
		boolean isRemote = player.worldObj.isRemote;
		
		Side side = isRemote ? Side.CLIENT : Side.SERVER;
		
		return this.get(player.getUniqueID());
	}
	
	public Class<T> getPlayerHookType()
	{
		return this.type;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public static void writeReference(IPlayerHook playerHook, ByteBuf buf)
	{
		buf.writeInt(playerHook.getManager().getID());
		
		playerHook.getProfile().writeToClient(buf);
	}
	
	public static IPlayerHook readReference(EntityPlayer player, ByteBuf buf)
	{
		PlayerHookManager manager = PlayerHookCore.proxy.getManagers().get(buf.readInt());
		
		PlayerProfile profile = new PlayerProfile();
		
		profile.readFromServer(buf);
    	
    	IPlayerHook playerHook = manager.get(player);
    	
    	return playerHook;
	}
	
	public static IPlayerHook readReference(Side side, ByteBuf buf)
	{
		PlayerHookManager manager = PlayerHookCore.proxy.getManagers().get(buf.readInt());
		
		PlayerProfile profile = new PlayerProfile();
		
		profile.readFromServer(buf);
    	
    	IPlayerHook playerHook = manager.get(profile.getUUID());
    	
    	return playerHook;
	}

	public void addPlayer(T player)
	{
		this.playerMap.put(player.getProfile().getUUID(), player);
	}

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
					PlayerHookCore.NETWORK.sendToServer(new MessagePlayerHookRequest(this, uuid));
					this.sentRequests.add(uuid);
				}
			}

			try
			{
				player = this.type.newInstance();

				PlayerProfile profile = new PlayerProfile();
				profile.setUUID(uuid);
				
				player.setProfile(profile);

				this.addPlayer(player);
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

	public void setPlayers(ArrayList<T> players)
	{
		for (T player : players)
		{
			this.addPlayer(player);
		}
	}

	public Collection<T> getPlayers()
	{
		return this.playerMap.values();
	}

}
