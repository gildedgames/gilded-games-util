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
	
	private InternalManager<T> clientHandler;

	private InternalManager<T> serverHandler;
	
	private Class<T> type;
	
	private int id;
	
	public PlayerHookManager(Class<T> playerHookType)
	{
		this.type = playerHookType;
		this.id = PlayerHookManager.managers.size();
		
		this.clientHandler = new InternalManager<T>(this, this.type);
		this.serverHandler = new InternalManager<T>(this, this.type);
		
		PlayerHookManager.managers.add(this);
	}
	
	public void clear()
	{
		this.clear(Side.SERVER);
		this.clear(Side.CLIENT);
	}

	public void clear(Side side)
	{
		if (side.isClient())
		{
			this.clientHandler = new InternalManager<T>(this, this.type);
		}
		else
		{
			this.serverHandler = new InternalManager<T>(this, this.type);
		}
	}

	public InternalManager<T> instance(Side side)
	{
		if (side.isClient())
		{
			return this.clientHandler;
		}
		else
		{
			return this.serverHandler;
		}
	}
	
	public T get(EntityPlayer player)
	{
		boolean isRemote = player.worldObj.isRemote;
		
		Side side = isRemote ? Side.CLIENT : Side.SERVER;
		
		return this.instance(side).get(player.getUniqueID());
	}
	
	public Class<T> getPlayerHookType()
	{
		return this.type;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public static List<PlayerHookManager> getManagers()
	{
		return PlayerHookManager.managers;
	}
	
	public static void writeReference(IPlayerHook playerHook, ByteBuf buf)
	{
		buf.writeInt(playerHook.getManager().getID());
		
		playerHook.getProfile().writeToClient(buf);
	}
	
	public static IPlayerHook readReference(EntityPlayer player, ByteBuf buf)
	{
		PlayerHookManager manager = PlayerHookManager.getManagers().get(buf.readInt());
		
		PlayerProfile profile = new PlayerProfile();
		
		profile.readFromServer(buf);
    	
    	IPlayerHook playerHook = manager.get(player);
    	
    	return playerHook;
	}
	
	public static IPlayerHook readReference(Side side, ByteBuf buf)
	{
		PlayerHookManager manager = PlayerHookManager.getManagers().get(buf.readInt());
		
		PlayerProfile profile = new PlayerProfile();
		
		profile.readFromServer(buf);
    	
    	IPlayerHook playerHook = manager.instance(side).get(profile.getUUID());
    	
    	return playerHook;
	}
	
	public static class InternalManager<T extends IPlayerHook>
	{
		
		private PlayerHookManager parent;
		
		private Class<T> playerHookType;
		
		private HashMap<UUID, T> playerMap = new HashMap<UUID, T>();

		private ArrayList<UUID> sentRequests = new ArrayList<UUID>();
		
		public InternalManager(PlayerHookManager parent, Class<T> type)
		{
			this.parent = parent;
			this.playerHookType = type;
		}
		
		private void addPlayer(T player)
		{
			this.playerMap.put(player.getProfile().getEntity().getUniqueID(), player);
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
						PlayerHookCore.NETWORK.sendToServer(new MessagePlayerHookRequest(this.parent, uuid));
						this.sentRequests.add(uuid);
					}
				}

				try
				{
					player = this.playerHookType.newInstance();
					
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

}
