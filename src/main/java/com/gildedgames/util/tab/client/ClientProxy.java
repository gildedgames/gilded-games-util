package com.gildedgames.util.tab.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.tab.TabBackpack;
import com.gildedgames.util.tab.server.ServerProxy;

public class ClientProxy extends ServerProxy
{
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void init()
	{
		ClientEvents clientEvents = new ClientEvents();
		
		MinecraftForge.EVENT_BUS.register(clientEvents);
		FMLCommonHandler.instance().bus().register(clientEvents);
		
		TabAPI.INSTANCE.setBackpackTab(new TabBackpack());
		
		TabAPI.INSTANCE.getInventoryGroup().getSide(Side.CLIENT).add(TabAPI.INSTANCE.getBackpackTab());
		
		TabAPI.INSTANCE.register(TabAPI.INSTANCE.getInventoryGroup());
	}

	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}
	
}
