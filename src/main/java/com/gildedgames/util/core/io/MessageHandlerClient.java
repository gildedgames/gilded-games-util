package com.gildedgames.util.core.io;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class MessageHandlerClient<REQ extends IMessage, RES extends IMessage> implements IMessageHandler<REQ, RES>
{
	@Override
	public RES onMessage(REQ message, MessageContext ctx)
	{
		return this.onMessage(message, Minecraft.getMinecraft().thePlayer);
	}

	public abstract RES onMessage(REQ message, EntityPlayer player);
}
