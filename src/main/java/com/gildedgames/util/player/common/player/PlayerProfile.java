package com.gildedgames.util.player.common.player;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PlayerProfile implements IPlayerProfile
{

	protected EntityPlayer player;

	protected UUID uuid;

	protected String username;

	protected boolean isLoggedIn, isDirty;

	public PlayerProfile()
	{

	}

	@Override
	public void entityInit(EntityPlayer player)
	{
		this.setEntity(player);

		this.setUUID(player.getUniqueID());

		this.username = player.getCommandSenderName();//TODO: May be wrong method.
	}

	@Override
	public void onUpdate()
	{
		if (this.player != null)
		{
			if (this.getUUID() == null)
			{
				this.setUUID(this.player.getUniqueID());
			}

			if (this.username == null)
			{
				this.username = this.player.getCommandSenderName();
			}
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
		return this.username;
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

		tag.setString("username", this.username);
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

		this.username = tag.getString("username");
	}

	@Override
	public void writeToClient(ByteBuf buf)
	{
		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());

		ByteBufUtils.writeUTF8String(buf, this.username);

		buf.writeBoolean(this.isLoggedIn);
	}

	@Override
	public void readFromServer(ByteBuf buf)
	{
		this.uuid = new UUID(buf.readLong(), buf.readLong());

		this.username = ByteBufUtils.readUTF8String(buf);

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

		return obj instanceof PlayerProfile && this.getUUID().equals(((PlayerProfile) obj).getUUID());
	}

}
