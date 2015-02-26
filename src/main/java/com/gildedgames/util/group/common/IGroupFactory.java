package com.gildedgames.util.group.common;

public interface IGroupFactory<P extends IGroup>
{
	
	P create(IGroupPool<P> parentPool);

}
