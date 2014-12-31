package com.gildedgames.util.playerhook.common.player;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.io_manager.io.NBT;
import com.gildedgames.util.io_manager.networking.ISyncable;

public interface IPlayerProfile extends NBT, ISyncable
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
