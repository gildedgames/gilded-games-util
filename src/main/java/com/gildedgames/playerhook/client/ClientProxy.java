package com.gildedgames.playerhook.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.playerhook.common.PlayerHookManager;
import com.gildedgames.playerhook.server.ServerProxy;

public class ClientProxy extends ServerProxy
{
	
	private List<PlayerHookManager> managers = new ArrayList<PlayerHookManager>();
	
	private Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void init()
	{
		
	}
	
	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}
	
	@Override
	public List<PlayerHookManager> getManagers()
	{
		return this.managers;
	}
	
}
