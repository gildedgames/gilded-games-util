package com.gildedgames.util.testutil.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.network.INetHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacketTest<P extends IMessage>
{

	public P readWrite()
	{
		P message = this.createDataSetPacket();
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		P message2 = this.createEmptyPacket();
		ByteBuf copy = Unpooled.copiedBuffer(buf);
		message2.fromBytes(copy);
		return message2;
	}

	public P readWriteAndHandle(Side side)
	{
		P read = this.readWrite();
		IMessageHandler<P, ?> handler = this.createHandler();
		Constructor<MessageContext> constructor;
		MessageContext instance = null;
		try
		{
			constructor = MessageContext.class.getConstructor(INetHandler.class, Side.class);
			instance = constructor.newInstance(null, side);
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		handler.onMessage(read, instance);
		return read;
	}

	/**
	 * Returns true if read and write would return true according to areEqual(P, P);
	 * @return
	 */
	public boolean areEqualReadAndWrite()
	{
		P message = this.createDataSetPacket();
		ByteBuf buf = Unpooled.buffer();
		message.toBytes(buf);
		P message2 = this.createEmptyPacket();
		ByteBuf copy = Unpooled.copiedBuffer(buf);
		message2.fromBytes(copy);
		return this.areEqual(message, message);
	}

	public abstract P createDataSetPacket();

	public abstract P createEmptyPacket();

	public abstract IMessageHandler<P, ?> createHandler();

	public abstract boolean areEqual(P expected, P actual);

}
