package com.gildedgames.util.instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.core.nbt.NBTHelper;

public abstract class Instance implements NBT
{
	/**
	 * Dynamic creation to preserve memory.
	 * When many instances on the server exist,
	 * all these arraylists may get crowdy. 
	 */
	private List<PlayerInstances> playersIn;

	private final InstanceHandler<?> instanceHandler;

	private final WorldServer world;

	private int id;

	public Instance(WorldServer world, int id, InstanceHandler<?> instanceHandler)
	{
		this.instanceHandler = instanceHandler;
		this.id = id;
		this.world = world;
	}

	public abstract BlockPos getSpawnPos(EntityPlayer player);

	public abstract BlockPosDimension getOutsidePos(EntityPlayer player);

	public abstract void generate();

	public abstract AxisAlignedBB getBoundingBox();

	protected abstract void writeInstance(NBTTagCompound output);

	protected abstract void readInstance(NBTTagCompound input);

	@Override
	public final void read(NBTTagCompound input)
	{
		for (NBTTagCompound tag : NBTHelper.getIterator(input, "players"))
		{
			PlayerInstances player = InstanceCore.INST.getPlayer(NBTHelper.getUUID(tag, "uuid"));
			this.playersIn().add(player);
			player.setInstance(this);
		}
		this.readInstance(input);
	}

	@Override
	public final void write(NBTTagCompound output)
	{
		NBTTagList tagList = new NBTTagList();
		for (PlayerInstances p : this.playersIn())
		{
			NBTTagCompound newTag = new NBTTagCompound();
			NBTHelper.setUUID(newTag, p.getProfile().getUUID(), "uuid");
			tagList.appendTag(tagList);
		}
		output.setTag("players", tagList);
		this.writeInstance(output);
	}

	public void join(EntityPlayerMP player)
	{
		PlayerInstances hook = InstanceCore.INST.getPlayer(player);
		if (!this.playersIn().contains(hook))
		{
			this.playersIn.add(hook);
			hook.setInstance(this);

			int playerDimId = player.worldObj.provider.getDimensionId();
			BlockPos pos = player.getPosition();
			BlockPosDimension outsidePos = new BlockPosDimension(pos.getX(), pos.getY(), pos.getZ(), playerDimId);
			hook.setOutside(outsidePos);

			BlockPos spawnPos = this.getSpawnPos(player);
			this.teleport(player, spawnPos, this.world);
		}
	}

	public void leave(EntityPlayerMP player)
	{
		PlayerInstances hook = InstanceCore.INST.getPlayer(player);
		if (this.playersIn().contains(hook))
		{
			this.playersIn.remove(hook);
			hook.setInstance(null);

			BlockPosDimension outsidePos = hook.outside();

			WorldServer world = MinecraftServer.getServer().worldServerForDimension(outsidePos.dimId());
			this.teleport(player, new BlockPos(outsidePos.getX(), outsidePos.getY(), outsidePos.getZ()), world);
		}
	}

	private void teleport(EntityPlayerMP player, BlockPos pos, WorldServer world)
	{
		if (player.worldObj != world)
		{
			InstanceTeleporter teleporter = new InstanceTeleporter(world);
			ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
			scm.transferPlayerToDimension(player, world.provider.getDimensionId(), teleporter);
			player.timeUntilPortal = player.getPortalCooldown();
		}
		player.posX = pos.getX();
		player.posY = pos.getY();
		player.posZ = pos.getZ();
		world.theChunkProviderServer.loadChunk(pos.getX() >> 4, pos.getZ() >> 4);
		player.playerNetServerHandler.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
	}

	private List<PlayerInstances> playersIn()
	{
		if (this.playersIn == null)
		{
			this.playersIn = new ArrayList<PlayerInstances>();
		}
		return this.playersIn;
	}

	public final Collection<PlayerInstances> getPlayersIn()
	{
		return this.playersIn();
	}

	public final InstanceHandler<?> getInstanceHandler()
	{
		return this.instanceHandler;
	}

	public final int getId()
	{
		return this.id;
	}

	public final World getWorld()
	{
		return this.world;
	}
}
