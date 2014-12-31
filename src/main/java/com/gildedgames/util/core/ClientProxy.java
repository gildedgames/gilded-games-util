package com.gildedgames.util.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.playerhook.common.IPlayerHookPool;

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
