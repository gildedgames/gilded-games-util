package com.gildedgames.util.player;

import static org.junit.Assert.fail;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.player.common.PlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.testutil.DataSet;
import com.gildedgames.util.testutil.TestPlayerHook;

public class PlayerServicesTest
{

	@Test
	public void testRegisterAndGetPools()
	{
		PlayerServices playerServices = new PlayerServices();
		PlayerHookPool<TestPlayerHook> playerHookPool = new PlayerHookPool<TestPlayerHook>("test", TestPlayerHook.class);
		playerServices.registerPlayerHookPool(playerHookPool);
		Assert.assertTrue(playerServices.getPools().contains(playerHookPool));
	}

	@Test
	public void testReadWriteHookReference()
	{
		PlayerServices playerServices = new PlayerServices();
		PlayerHookPool<TestPlayerHook> playerHookPool = new PlayerHookPool<TestPlayerHook>("test", TestPlayerHook.class);
		playerServices.registerPlayerHookPool(playerHookPool);
		List<IPlayerHook> playerHooks = DataSet.iPlayerHooks(playerHookPool);
		for (IPlayerHook playerHook : playerHooks)
		{
			ByteBuf buf = Unpooled.buffer();
			playerServices.writeHookReference(playerHook, buf);
			IPlayerHook playerHookRead = playerServices.readHookReference(Side.SERVER, buf);
			Assert.assertEquals(playerHook, playerHookRead);
		}
	}

	@Test
	public void testReadHookReferenceEntityPlayerByteBuf()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReadHookReferenceSideByteBuf()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterPlayerHookPool()
	{
		fail("Not yet implemented");
	}

}
