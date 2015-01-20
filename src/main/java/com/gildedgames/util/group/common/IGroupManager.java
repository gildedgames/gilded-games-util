package com.gildedgames.util.group.common;

import java.util.List;

public interface IGroupManager
{
	
	List<IGroup> getGroups();
	
	void setGroups(List<IGroup> groups);
	
	void add(IGroup group);
	
	void remove(IGroup group);
	
}
