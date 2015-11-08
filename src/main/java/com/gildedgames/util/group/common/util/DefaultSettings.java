package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupSettings;

public class DefaultSettings implements IGroupSettings
{

	@Override
	public boolean canPlayerJoinMultipleGroups()
	{
		return false;
	}

	@Override
	public boolean groupRemovedWhenEmpty()
	{
		return true;
	}

	@Override
	public boolean duplicateNamesAllowed()
	{
		return false;
	}

}
