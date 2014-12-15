package com.gildedgames.playerhook.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.playerhook.server.ServerProxy;

public class ClientProxy extends ServerProxy
{
	
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
	
}
