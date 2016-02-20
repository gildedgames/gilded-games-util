package com.gildedgames.util.modules.player.common.player;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.io.IOSyncable;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;

public interface IPlayerHook extends NBT, IOSyncable<ByteBuf, ByteBuf>
{

	@SuppressWarnings("rawtypes")
	IPlayerHookPool getParentPool();

	void entityInit(EntityPlayer player);

	IPlayerProfile getProfile();

}
