package com.gildedgames.util.player.common.player;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.IOManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerProfile implements IPlayerProfile
{

	protected EntityPlayer player;

	protected UUID uuid;

	protected boolean isLoggedIn, isDirty;

	public PlayerProfile()
	{

	}

	@Override
	public void entityInit(EntityPlayer player)
	{
		this.setEntity(player);

		this.setUUID(player.getUniqueID());
	}

	@Override
	public void onUpdate()
	{
		if (this.getUUID() == null && this.player != null)
		{
			this.setUUID(this.player.getUniqueID());
		}
	}

	@Override
	public boolean isLoggedIn()
	{
		return this.isLoggedIn;
	}

	@Override
	public void setLoggedIn(boolean isLoggedIn)
	{
		this.isLoggedIn = isLoggedIn;
	}

	@Override
	public String getUsername()
	{
		return this.player.getCommandSenderEntity().getName();
	}

	@Override
	public UUID getUUID()
	{
		return this.uuid;
	}

	@Override
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;

		this.markDirty();
	}

	@Override
	public EntityPlayer getEntity()
	{
		return this.player;
	}

	@Override
	public void setEntity(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setLong("UUIDMost", this.uuid.getMostSignificantBits());
		tag.setLong("UUIDLeast", this.uuid.getLeastSignificantBits());
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		if (tag.hasKey("UUIDMost", 4) && tag.hasKey("UUIDLeast", 4))
		{
			this.uuid = new UUID(tag.getLong("UUIDMost"), tag.getLong("UUIDLeast"));
		}
		else if (tag.hasKey("UUID", 8))
		{
			this.uuid = UUID.fromString(tag.getString("UUID"));
		}
	}

	@Override
	public void writeToClient(ByteBuf buf)
	{
		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());

		buf.writeBoolean(this.isLoggedIn);
	}

	@Override
	public void readFromServer(ByteBuf buf)
	{
		this.uuid = new UUID(buf.readLong(), buf.readLong());
		this.isLoggedIn = buf.readBoolean();
	}

	@Override
	public void writeToServer(ByteBuf buf)
	{

	}

	@Override
	public void readFromClient(ByteBuf buf)
	{

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

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof PlayerProfile && this.getUUID().equals(((PlayerProfile) obj).getUUID()))
		{
			return true;
		}
		return false;
	}

}
