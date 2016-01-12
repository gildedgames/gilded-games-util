package com.gildedgames.util.universe.common.player;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;
import com.gildedgames.util.universe.UniverseCore;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.util.IUniverse;
import com.mojang.authlib.GameProfile;

public class PlayerUniverse implements IPlayerHook
{

	private EntityPlayer player;

	private final IPlayerProfile profile;

	private boolean isDirty;

	private String universeID = UniverseAPI.instance().getMinecraftUniverseID();

	private final Map<String, EntityPlayer> universeInstances = new HashMap<>();

	private final IPlayerHookPool<PlayerUniverse> pool;
	
	private PlayerUniverse()
	{
		this(null, null);
	}

	public PlayerUniverse(IPlayerHookPool<PlayerUniverse> pool, IPlayerProfile profile)
	{
		this.profile = profile;
		this.pool = pool;
	}
	
	public static PlayerUniverse createEventHandler()
	{
		return new PlayerUniverse();
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
		this.player = this.getProfile().getEntity();

		String minecraftUniverse = UniverseAPI.instance().getMinecraftUniverseID();

		if (this.universeInstances.get(minecraftUniverse) == null)
		{
			this.universeInstances.put(minecraftUniverse, this.player);
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			PlayerUniverse player = UniverseCore.locate().getPlayer((EntityPlayer) event.entity);
			
			player.player = player.getProfile().getEntity();
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setString("universeID", this.universeID);

		NBTTagList universeInstances = new NBTTagList();

		for (Map.Entry<String, EntityPlayer> entry : this.universeInstances.entrySet())
		{
			if (entry.getValue() == null)
			{
				continue;
			}

			NBTTagCompound entryTag = new NBTTagCompound();

			entryTag.setString("universeID", entry.getKey());

			NBTTagCompound playerTag = new NBTTagCompound();

			entry.getValue().writeToNBT(playerTag);

			entryTag.setTag("entityPlayer", playerTag);

			universeInstances.appendTag(entryTag);
		}

		tag.setTag("universeInstances", universeInstances);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		this.universeID = tag.getString("universeID");

		NBTTagList tagList = (NBTTagList) tag.getTag("universeInstances");

		for (int count = 0; count < tagList.tagCount(); count++)
		{
			NBTTagCompound newTag = tagList.getCompoundTagAt(count);

			MinecraftServer server = MinecraftServer.getServer();

			//This was in the code but unused.
			//ServerConfigurationManager scm = server.getConfigurationManager();

			WorldServer world = server.worldServerForDimension(0);

			EntityPlayerMP newPlayer = new EntityPlayerMP(server, world, new GameProfile(this.getProfile().getUUID(), this.getProfile().getUsername()), new ItemInWorldManager(world));

			newPlayer.readFromNBT(newTag.getCompoundTag("entityPlayer"));

			this.universeInstances.put(newTag.getString("universeID"), newPlayer);
		}
	}

	@Override
	public void syncTo(ByteBuf buf, SyncSide to)
	{
		if (to.isClient())
		{
			ByteBufUtils.writeUTF8String(buf, this.universeID);
		}
	}

	@Override
	public void syncFrom(ByteBuf buf, SyncSide from)
	{
		if (from.isServer())
		{
			this.universeID = ByteBufUtils.readUTF8String(buf);
		}
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;
	}

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	public IUniverse getUniverse()
	{
		return UniverseAPI.instance().getFromID(this.universeID);
	}

	public String getUniverseID()
	{
		return this.universeID;
	}

	public void setUniverseID(String universe)
	{
		if (!(this.player instanceof EntityPlayerMP))
		{
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) this.player;

		ServerConfigurationManager scm = player.mcServer.getConfigurationManager();

		WorldServer world = player.mcServer.worldServerForDimension(0);

		EntityPlayerMP clonedPlayer = new EntityPlayerMP(player.mcServer, world, player.getGameProfile(), new ItemInWorldManager(world));

		clonedPlayer.clonePlayer(player, true);

		/*NBTTagCompound playerTag = new NBTTagCompound();
		
		player.writeToNBT(playerTag);
		
		clonedPlayer.readFromNBT(playerTag);*/

		clonedPlayer.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);

		this.universeInstances.put(this.universeID, clonedPlayer);

		this.universeID = universe;

		if (this.universeInstances.get(this.universeID) == null)
		{
			this.universeInstances.put(this.universeID, new EntityPlayerMP(player.mcServer, (WorldServer) player.worldObj, player.getGameProfile(), new ItemInWorldManager(player.worldObj)));
		}

		EntityPlayerMP newPlayer = (EntityPlayerMP) this.universeInstances.get(this.universeID);

		if (this.player.dimension != newPlayer.dimension)
		{
			UniverseCore.teleportToDimension(player, newPlayer.dimension);
		}

		this.player.clonePlayer(newPlayer, true);

		/*playerTag = new NBTTagCompound();
		
		newPlayer.writeToNBT(playerTag);
		
		this.player.readFromNBT(playerTag);*/

		player.playerNetServerHandler.setPlayerLocation(newPlayer.posX, newPlayer.posY, newPlayer.posZ, newPlayer.rotationYaw, newPlayer.rotationPitch);
		player.theItemInWorldManager.setWorld((WorldServer) newPlayer.worldObj);
		scm.updateTimeAndWeatherForPlayer(player, (WorldServer) newPlayer.worldObj);
		scm.syncPlayerInventory(player);

		this.markDirty();
	}

	@Override
	public IPlayerHookPool<PlayerUniverse> getParentPool()
	{
		return this.pool;
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.profile;
	}
	
}
