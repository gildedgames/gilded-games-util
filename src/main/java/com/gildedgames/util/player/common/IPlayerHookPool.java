package com.gildedgames.util.player.common;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.player.common.player.IPlayerHook;

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
