package com.gildedgames.util.testutil.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.nbt.NBTTagCompound;

import org.junit.Assert;

import com.gildedgames.util.core.nbt.NBT;
import com.gildedgames.util.io_manager.networking.ISyncable;

public class TestIOUtil
{

	public static ByteBuf createBuf()
	{
		return Unpooled.buffer();
	}

	public static ByteBuf copyBuffer(ByteBuf buf)
	{
		return Unpooled.copiedBuffer(buf);
	}

	public static <T extends NBT> T copyNBTInEmpty(T expected, T empty)
	{
		NBTTagCompound tag = new NBTTagCompound();
		expected.write(tag);
		empty.read(tag);
		return empty;
	}

	/**
	 * Test if two instances of T are equal after writing and reading.
	 * Note: You need to implement .equals for T
	 */
	public static <T extends NBT> void testNBTWrite(T expected, T empty)
	{
		copyNBTInEmpty(expected, empty);
		Assert.assertEquals(expected, empty);
	}

	public static <T extends ISyncable> T readServerToClient(T server, T client)
	{
		ByteBuf buf = createBuf();
		server.writeToClient(buf);
		ByteBuf copied = copyBuffer(buf);
		client.readFromServer(copied);
		return client;
	}

	/**
	 * Test if two instances of T are equal after writing and reading.
	 * Note: You need to implement .equals for T
	 */
	public static <T extends ISyncable> void testReadServerToClient(T server, T client)
	{
		readServerToClient(server, client);
		Assert.assertEquals(server, client);
	}

	public static <T extends ISyncable> T readClientToServer(T client, T server)
	{
		ByteBuf buf = createBuf();
		client.writeToServer(buf);
		ByteBuf copied = copyBuffer(buf);
		server.readFromClient(copied);
		return server;
	}

	/**
	 * Test if two instances of T are equal after writing and reading.
	 * Note: You need to implement .equals for T
	 */
	public static <T extends ISyncable> void testReadClientToServer(T client, T server)
	{
		readClientToServer(client, server);
		Assert.assertEquals(client, server);
	}

	public static void testISyncable(ISyncable syncable)
	{
		syncable.markDirty();
		Assert.assertTrue(syncable.isDirty());
		syncable.markClean();
		Assert.assertFalse(syncable.isDirty());
	}

	public static DataOutputStream dataOutputStream()
	{
		return new DataOutputStream(new ByteArrayOutputStream());
	}
}
