package com.gildedgames.util.modules.tab.common.networking.packet;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.tab.TabModule;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabGroup;
import com.gildedgames.util.modules.tab.common.util.ITabGroupHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public class PacketOpenTab implements IMessage
{

	private int tabGroupIndex, tabIndex, containerID;
	
	private boolean openContainer = false;

	public PacketOpenTab() { }

	public PacketOpenTab(ITab tab)
	{
		for (Map.Entry<Integer, ITabGroupHandler> entry : TabModule.api().getRegisteredTabGroups().entrySet())
		{
			int groupIndex = entry.getKey();

			ITabGroupHandler tabGroup = entry.getValue();

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

	public static class HandlerClient extends MessageHandlerClient<PacketOpenTab, IMessage>
	{

		@Override
		public IMessage onMessage(PacketOpenTab message, EntityPlayer player)
		{
			if (message.openContainer)
			{
				Minecraft.getMinecraft().thePlayer.openContainer.windowId = message.containerID;
			}

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketOpenTab, PacketOpenTab>
	{
		@Override
		public PacketOpenTab onMessage(PacketOpenTab message, EntityPlayer player)
		{
			if (player instanceof EntityPlayerMP)
			{
				EntityPlayerMP playerMp = (EntityPlayerMP) player;

				if (message.tabGroupIndex < TabModule.api().getRegisteredTabGroups().size())
				{
					ITabGroupHandler tabGroupHandler = TabModule.api().getRegisteredTabGroups().get(message.tabGroupIndex);

					if (tabGroupHandler == null)
					{
						return null;
					}

					ITabGroup tabGroup = tabGroupHandler.getSide(Side.SERVER);

					if (message.tabIndex < tabGroup.getTabs().size())
					{
						ITab tab = tabGroup.getTabs().get(message.tabIndex);

						Container container = tab.getCurrentContainer(playerMp, playerMp.worldObj, (int) playerMp.posX, (int) playerMp.posY, (int) playerMp.posZ);

						if (container != null)
						{
							playerMp.getNextWindowId();
							playerMp.closeContainer();

							int windowID = playerMp.currentWindowId;

							playerMp.openContainer = container;
							playerMp.openContainer.windowId = windowID;

							playerMp.openContainer.onCraftGuiOpened(playerMp);

							return new PacketOpenTab(message.tabGroupIndex, message.tabIndex, windowID);
						}

						tab.onOpen(player);
					}
				}
			}

			return null;
		}
	}
}
