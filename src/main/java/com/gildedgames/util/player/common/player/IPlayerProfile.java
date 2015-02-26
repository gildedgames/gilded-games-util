package com.gildedgames.util.player.common.player;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.networking.ISyncable;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

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
