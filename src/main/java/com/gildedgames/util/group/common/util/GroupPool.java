package com.gildedgames.util.group.common.util;

import com.gildedgames.util.group.common.IGroup;
import com.gildedgames.util.group.common.IGroupFactory;
import com.gildedgames.util.group.common.IGroupPool;

import java.util.ArrayList;
import java.util.List;

public class GroupPool<G extends IGroup> implements IGroupPool<G>
{
	
	protected final String id;
	
	protected final IGroupFactory<G> factory;
	
	protected List<G> groups = new ArrayList<G>();

	public GroupPool(String id, IGroupFactory<G> factory)
	{
		this.id = id;
		this.factory = factory;
	}
	
	@Override
	public String getID()
	{
		return this.id;
	}
	
	@Override
	public List<G> getGroups()
	{
		return this.groups;
	}

	@Override
	public void setGroups(List<G> groups)
	{
		this.groups = groups;
	}

	@Override
	public void add(G group)
	{
		this.groups.add(group);
	}

	@Override
	public void remove(G group)
	{
		this.groups.remove(group);
	}

	@Override
	public G get(String name)
	{
		for (G group : this.groups)
		{
			if (group.getName().equalsIgnoreCase(name))
			{
				return group;
			}
		}
		
		return null;
	}

}
