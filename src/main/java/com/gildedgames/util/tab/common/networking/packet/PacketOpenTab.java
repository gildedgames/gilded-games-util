package com.gildedgames.util.tab.common.networking.packet;

import io.netty.buffer.ByteBuf;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.tab.common.TabAPI;
import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.tab.common.util.ITabGroup;
import com.gildedgames.util.tab.common.util.ITabGroupHandler;
import com.gildedgames.util.tab.common.util.TabGroupHandler;

import cpw.mods.fml.relauncher.Side;

public class PacketOpenTab extends CustomPacket<PacketOpenTab>
{

	private int tabGroupIndex, tabIndex, containerID;
	
	private boolean openContainer = false;

	public PacketOpenTab()
	{
	}

	public PacketOpenTab(ITab tab)
	{
		Iterator it = TabAPI.INSTANCE.getRegisteredTabGroups().entrySet().iterator();
		
	    while (it.hasNext())
	    {
	        Map.Entry<Integer, TabGroupHandler> pairs = (Map.Entry)it.next();
	        
	        int groupIndex = pairs.getKey();
	        TabGroupHandler tabGroup = pairs.getValue();
	        
			int index = 0;
			
			for (ITab groupTab : tabGroup.getSide(Side.CLIENT).getTabs())
			{
				if (tab == groupTab)
				{
					this.tabGroupIndex = groupIndex;
					this.tabIndex = index;
					return;
				}
				
				index++;
			}
		}
	}
	
	public PacketOpenTab(int tabGroupIndex, int tabIndex, int containerID)
	{
		this.tabGroupIndex = tabGroupIndex;
		this.tabIndex = tabIndex;
		this.containerID = containerID;
		
		this.openContainer = true;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tabGroupIndex = buf.readInt();
		this.tabIndex = buf.readInt();
		this.containerID = buf.readInt();
		
		this.openContainer = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.tabGroupIndex);
		buf.writeInt(this.tabIndex);
		buf.writeInt(this.containerID);
		
		buf.writeBoolean(this.openContainer);
	}

	@Override
	public void handleClientSide(PacketOpenTab message, EntityPlayer player)
	{
		if (message.openContainer)
		{
			Minecraft.getMinecraft().thePlayer.openContainer.windowId = message.containerID;
		}
	}

	@Override
	public void handleServerSide(PacketOpenTab message, EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP entityPlayer = (EntityPlayerMP) player;

			if (this.tabGroupIndex < TabAPI.INSTANCE.getRegisteredTabGroups().size())
			{
				ITabGroupHandler tabGroupHandler = TabAPI.INSTANCE.getRegisteredTabGroups().get(this.tabGroupIndex);
				
				if (tabGroupHandler == null)
				{
					return;
				}
				
				ITabGroup tabGroup = tabGroupHandler.getSide(Side.SERVER);
				
				if (this.tabIndex < tabGroup.getTabs().size())
				{
					ITab tab = tabGroup.getTabs().get(this.tabIndex);
	
					Container container = (Container) tab.getCurrentContainer(entityPlayer, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
	
					if (container != null)
					{
						entityPlayer.getNextWindowId();
						entityPlayer.closeContainer();
	
						int windowID = entityPlayer.currentWindowId;
	
						entityPlayer.openContainer = container;
						entityPlayer.openContainer.windowId = windowID;
	
						UtilCore.NETWORK.sendTo(new PacketOpenTab(this.tabGroupIndex, this.tabIndex, windowID), entityPlayer);
					}
					
					tab.onOpen(player);
				}
			}
		}
	}

}
