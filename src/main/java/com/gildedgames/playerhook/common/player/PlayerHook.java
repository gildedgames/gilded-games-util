package com.gildedgames.playerhook.common.player;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.PlayerHookCore;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerInfo;
import com.gildedgames.playerhook.common.networking.messages.MessagePlayerInfoClient;
import com.gildedgames.playerhook.common.util.INBT;
import com.gildedgames.playerhook.common.util.ISyncable;

public class PlayerHook implements ISyncable, INBT
{
	
	private String username;
	
	private UUID uuid;

	private EntityPlayer player;
	
	private boolean isLoggedIn, isDirty;

	public static PlayerHook get(EntityPlayer player)
	{
		return get(player.worldObj.isRemote ? Side.CLIENT : Side.SERVER, player.getCommandSenderEntity().getName());
	}

	public static PlayerHook get(Side side, String username)
	{
		return PlayerHookManager.instance(side).getPlayerHook(username);
	}

	public PlayerHook()
	{
		
	}

	public PlayerHook(String username)
	{
		this();
		
		this.username = username;
	}

	public void onJoinWorld(EntityPlayer player)
	{
		this.player = player;
		
		this.uuid = this.player.getUniqueID();
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public EntityPlayer getEntity()
	{
		return this.player;
	}
	
	public void setEntity(EntityPlayer player)
	{
		this.player = player;
	}
	
	public boolean isLoggedIn()
	{
		return this.isLoggedIn;
	}
	
	public void setLoggedIn(boolean isLoggedIn)
	{
		this.isLoggedIn = isLoggedIn;
	}

	public boolean onLivingAttack(DamageSource source)
	{
		return true;
	}

	public void onUpdate()
	{
		if (this.uuid == null)
		{
			this.uuid = this.player.getUniqueID();
		}
		
		if (this.player.worldObj.isRemote)
		{
			this.onClientUpdate();
		}
		else
		{
			this.onServerUpdate();
		}
	}
	
	public void onClientUpdate()
	{
		this.refreshServer();
	}
	
	public void onServerUpdate()
	{
		this.refreshClients();
	}

	public void onDeath()
	{
		
	}

	public void onChangedDimension()
	{
		
	}

	public void onRespawn()
	{
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("username", this.username);
		
		tag.setLong("UUIDMost", this.uuid.getMostSignificantBits());
        tag.setLong("UUIDLeast", this.uuid.getLeastSignificantBits());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.username = tag.getString("username");
		
		if (tag.hasKey("UUIDMost", 4) && tag.hasKey("UUIDLeast", 4))
        {
            this.uuid = new UUID(tag.getLong("UUIDMost"), tag.getLong("UUIDLeast"));
        }
        else if (tag.hasKey("UUID", 8))
        {
            this.uuid = UUID.fromString(tag.getString("UUID"));
        }
	}

	public void refreshServer()
	{
		if (this.isDirty())
		{
			PlayerHookCore.NETWORK.sendToServer(new MessagePlayerInfoClient(this));
			this.markClean();
		}
	}
	
	public void refreshClients()
	{
		if (this.isDirty())
		{
			PlayerHookCore.NETWORK.sendToAll(new MessagePlayerInfo(this));
			this.markClean();
		}
	}
	
	@Override
	public void writeServerData(ByteBuf buf)
	{
		buf.writeBoolean(this.isLoggedIn);
	}

	@Override
	public void readServerData(ByteBuf buf)
	{
		this.isLoggedIn = buf.readBoolean();
	}
	
	@Override
	public void writeClientData(ByteBuf buf)
	{
		
	}

	@Override
	public void readClientData(ByteBuf buf)
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
