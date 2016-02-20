package com.gildedgames.util.modules.player;

import static org.junit.Assert.fail;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.modules.player.common.PlayerHookPool;
import com.gildedgames.util.modules.player.common.player.IPlayerHook;
import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.player.TestPlayerHook;
import com.gildedgames.util.testutil.player.TestPlayerHookFactory;

public class PlayerServicesTest
{

	private PlayerHookPool<TestPlayerHook> createPool()
	{
		return new PlayerHookPool<TestPlayerHook>("test", new TestPlayerHookFactory(), Side.SERVER);
	}

	@Test
	public void testRegisterAndGetPools()
	{
		PlayerServices playerServices = new PlayerServices();
		PlayerHookPool<TestPlayerHook> playerHookPool = this.createPool();
		playerServices.registerPlayerHookPool(playerHookPool);
		Assert.assertTrue(playerServices.getPools().contains(playerHookPool));
	}

	@Test
	public void testReadWriteHookReference()
	{
		PlayerServices playerServices = new PlayerServices();
		PlayerHookPool<TestPlayerHook> playerHookPool = this.createPool();
		playerServices.registerPlayerHookPool(playerHookPool);
		List<IPlayerHook> playerHooks = GGUtilDataSet.iPlayerHooks(playerHookPool);
		for (IPlayerHook playerHook : playerHooks)
		{
			ByteBuf buf = Unpooled.buffer();
			playerServices.writeHookReference(playerHook, buf);
			ByteBuf readBuf = Unpooled.copiedBuffer(buf);
			IPlayerHook playerHookRead = playerServices.readHookReference(Side.SERVER, readBuf);
			Assert.assertSame(playerHook, playerHookRead);
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
