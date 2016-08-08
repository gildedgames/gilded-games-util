package com.gildedgames.util.modules.instances;

import com.gildedgames.util.io_manager.io.NBT;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface Instance extends NBT
{

	void onJoin(EntityPlayer player);

	void onLeave(EntityPlayer player);

	List<EntityPlayer> getPlayers();

	int getDimIdInside();

}
