package com.gildedgames.util.modules.player.common.player;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.gildedgames.util.testutil.GGUtilDataSet;
import com.gildedgames.util.testutil.io.TestIOUtil;

public class PlayerProfileTest
{

	private PlayerProfile create()
	{
		return new PlayerProfile();
	}

	private PlayerProfile dataSet()
	{
		PlayerProfile profile = this.create();
		profile.setUUID(GGUtilDataSet.uuid());
		profile.setLoggedIn(true);
		return profile;
	}

	@Test
	public void testLoggedIn()
	{
		PlayerProfile profile = this.create();
		profile.setLoggedIn(true);
		Assert.assertTrue(profile.isLoggedIn());

		profile.setLoggedIn(false);
		Assert.assertFalse(profile.isLoggedIn());
	}

	@Test
	public void testGetUUID()
	{
		PlayerProfile profile = this.create();
		UUID uuid = GGUtilDataSet.uuid();
		profile.setUUID(uuid);
		Assert.assertEquals(profile.getUUID(), uuid);
	}

	@Test
	public void testReadWrite()
	{
		PlayerProfile profile = this.create();
		profile.setUUID(GGUtilDataSet.uuid());
		TestIOUtil.testNBTWrite(profile, this.create());
	}

	@Test
	public void testWriteToClient()
	{
		PlayerProfile profile = this.dataSet();
		PlayerProfile copied = this.create();
		TestIOUtil.readServerToClient(profile, copied);
		Assert.assertTrue(profile.equals(copied) && profile.isLoggedIn() == copied.isLoggedIn());
	}

	@Test
	public void testISyncable()
	{
		TestIOUtil.testISyncable(this.create());
	}

}
