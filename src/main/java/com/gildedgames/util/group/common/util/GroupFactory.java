package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroupFactory;
import com.gildedgames.util.group.common.IGroupPool;

public class GroupFactory implements IGroupFactory<Group>
{

	@Override
	public Group create(IGroupPool<Group> parentPool)
	{
		return new Group(parentPool, new GroupPermsOpen());
	}
	
}