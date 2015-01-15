package com.gildedgames.util.playerhook.common;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.playerhook.common.player.IPlayerHook;

public interface IPlayerHookPool<T extends IPlayerHook>
{

	String getName();

	Class<T> getPlayerHookType();

	T get(EntityPlayer player);

	T get(UUID playerUuid);

	T createEmpty();

	void add(T playerHook);

	void setPlayerHooks(List<T> players);

	Collection<T> getPlayerHooks();

	void clear();

	boolean shouldSave();

}
