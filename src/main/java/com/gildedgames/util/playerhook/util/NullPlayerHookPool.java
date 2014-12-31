package com.gildedgames.util.playerhook.util;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import scala.actors.threadpool.Arrays;

import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;

public class NullPlayerHookPool implements IPlayerHookPool<NullPlayerHook>
{
	
	private static NullPlayerHookPool INSTANCE;
	
	private List list;

	private NullPlayerHookPool()
	{
		this.list = Arrays.asList(new IPlayerHook[]{ NullPlayerHook.instance() });
	}
	
	public static NullPlayerHookPool instance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new NullPlayerHookPool();
		}
		
		return INSTANCE;
	}

	@Override
	public String getName() { return "NULL"; }

	@Override
	public Class getPlayerHookType() { return NullPlayerHook.class; }

	@Override
	public NullPlayerHook get(EntityPlayer player) { return NullPlayerHook.instance(); }

	@Override
	public NullPlayerHook get(UUID playerUuid) { return NullPlayerHook.instance(); }

	@Override
	public void add(NullPlayerHook playerHook) {}

	@Override
	public void setPlayerHooks(List players) {}

	@Override
	public Collection getPlayerHooks() { return this.list; }

	@Override
	public void clear() {}

	@Override
	public boolean shouldSave() { return false; }

}
