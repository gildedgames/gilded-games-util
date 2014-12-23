package com.gildedgames.playerhook.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.playerhook.common.PlayerHookManager;

public class ServerProxy
{
	
	private List<PlayerHookManager> managers = new ArrayList<PlayerHookManager>();

	public void init()
	{
		
	}
	
	public EntityPlayer getPlayer()
	{
		return null;
	}
	
	public List<PlayerHookManager> getManagers()
	{
		return this.managers;
	}
	
}
