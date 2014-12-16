package com.gildedgames.playerhook.common.player;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.playerhook.common.util.INBT;
import com.gildedgames.playerhook.common.util.ISyncable;

public class PlayerProfile implements INBT, ISyncable
{

	protected EntityPlayer player;
	
	protected UUID uuid;
	
	protected boolean isLoggedIn, isDirty;
	
	public PlayerProfile()
	{
		
	}
	
	public void entityInit(EntityPlayer player)
	{
		this.setEntity(player);
		
		this.setUUID(player.getUniqueID());
	}
	
	public void onUpdate()
	{
		if (this.getUUID() == null)
		{
			this.setUUID(this.player.getUniqueID());
		}
	}

	public boolean isLoggedIn()
	{
		return this.isLoggedIn;
	}
	
	public void setLoggedIn(boolean isLoggedIn)
	{
		this.isLoggedIn = isLoggedIn;
	}
	
	public String getUsername()
	{
		return this.player.getCommandSenderEntity().getName();
	}

	public UUID getUUID()
	{
		return this.uuid;
	}
	
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
		
		this.markDirty();
	}
	
	public EntityPlayer getEntity()
	{
		return this.player;
	}
	
	public void setEntity(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setLong("UUIDMost", this.uuid.getMostSignificantBits());
        tag.setLong("UUIDLeast", this.uuid.getLeastSignificantBits());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
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
	
}
