package com.gildedgames.util.modules.tab.common.networking.packet;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.io.CustomPacket;
import com.gildedgames.util.modules.tab.TabModule;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabGroup;
import com.gildedgames.util.modules.tab.common.util.ITabGroupHandler;
import com.gildedgames.util.modules.tab.common.util.TabGroupHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public class PacketOpenTab extends CustomPacket<PacketOpenTab>
{

	private int tabGroupIndex, tabIndex, containerID;
	
	private boolean openContainer = false;

	public PacketOpenTab()
	{
	}

	public PacketOpenTab(ITab tab)
	{

		for (Object o : TabModule.api().getRegisteredTabGroups().entrySet())
		{
			Map.Entry<Integer, TabGroupHandler> pairs = (Map.Entry) o;

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

			if (this.tabGroupIndex < TabModule.api().getRegisteredTabGroups().size())
			{
				ITabGroupHandler tabGroupHandler = TabModule.api().getRegisteredTabGroups().get(this.tabGroupIndex);
				
				if (tabGroupHandler == null)
				{
					return;
				}
				
				ITabGroup tabGroup = tabGroupHandler.getSide(Side.SERVER);
				
				if (this.tabIndex < tabGroup.getTabs().size())
				{
					ITab tab = tabGroup.getTabs().get(this.tabIndex);
	
					Container container = tab.getCurrentContainer(entityPlayer, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
	
					if (container != null)
					{
						entityPlayer.getNextWindowId();
						entityPlayer.closeContainer();
	
						int windowID = entityPlayer.currentWindowId;
	
						entityPlayer.openContainer = container;
						entityPlayer.openContainer.windowId = windowID;
	
						UtilModule.NETWORK.sendTo(new PacketOpenTab(this.tabGroupIndex, this.tabIndex, windowID), entityPlayer);
					}
					
					tab.onOpen(player);
				}
			}
		}
	}

}
