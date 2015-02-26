package com.gildedgames.util.player.common;

import com.gildedgames.util.player.common.player.IPlayerHook;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IPlayerHookPool<T extends IPlayerHook>
{

	String getName();

	IPlayerHookFactory<T> getFactory();

	T get(EntityPlayer player);

	T get(UUID playerUuid);

	void add(T playerHook);

	void setPlayerHooks(List<T> players);

	Collection<T> getPlayerHooks();

	void clear();

	boolean shouldSave();

}
