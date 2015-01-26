package com.gildedgames.util.group.common;

import java.util.List;

public interface IGroupPool<T extends IGroup>
{
	
	String getID();
	
	List<T> getGroups();
	
	void setGroups(List<T> groups);
	
	void add(T group);
	
	void remove(T group);
	
	T get(String name);
	
}
