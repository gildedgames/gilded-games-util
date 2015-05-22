package com.gildedgames.util.testutil;

import com.gildedgames.util.testutil.instances.DefaultHandler;

public class TestServices
{
	private DefaultHandler handler;

	public TestServices()
	{

	}

	protected void setHandler(DefaultHandler handler)
	{
		this.handler = handler;
	}

	public DefaultHandler getHandler()
	{
		return this.handler;
	}

}
