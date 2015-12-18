package com.gildedgames.util.player.common.player;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.io.IOSyncable;

public interface IPlayerProfile extends NBT, IOSyncable<ByteBuf, ByteBuf>
{
	
	void entityInit(EntityPlayer player);
	
	void onUpdate();
	
	boolean isLoggedIn();
	
	void setLoggedIn(boolean isLoggedIn);
	
	String getUsername();
	
	UUID getUUID();
	
	void setUUID(UUID uuid);
	
	EntityPlayer getEntity();
	
	void setEntity(EntityPlayer player);
	
}
