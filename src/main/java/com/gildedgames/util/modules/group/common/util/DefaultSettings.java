package com.gildedgames.util.modules.group.common.util;

import com.gildedgames.util.modules.group.common.IGroupSettings;

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
