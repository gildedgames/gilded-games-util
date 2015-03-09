package com.gildedgames.util.ui.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Iterators;

public class MouseEventPool implements Iterable<MouseEvent>
{
	
	protected final MouseEvent[] events;

	public MouseEventPool(MouseEvent... events)
	{
		this.events = events;
	}
	
	public int size()
	{
		return this.events.length;
	}
	
	public MouseEvent get(int index)
	{
		if (index >= this.events.length)
		{
			 return null;
		}
		
		return this.events[index];
	}
	
	public boolean contains(Object o)
	{
		return indexOf(o) >= 0;
	}
	
	public boolean containsAll(Collection<?> c)
	{
		return Arrays.asList(this.events).containsAll(c);
	}
	
    public int indexOf(Object o)
    {
        return ArrayUtils.indexOf(this.events, o);
    }
	
	public MouseEventPool getFrom(ButtonState state)
	{
		List<MouseEvent> events = new ArrayList<MouseEvent>();
		
		for (MouseEvent event : this)
		{
			if (event != null && event.getState() == state)
			{
				events.add(event);
			}
		}
		
		return new MouseEventPool(events.toArray(new MouseEvent[events.size()]));
	}
	
	public MouseEventPool getFrom(MouseButton button)
	{
		List<MouseEvent> events = new ArrayList<MouseEvent>();
		
		for (MouseEvent event : this)
		{
			if (event != null && event.getButton() == button)
			{
				events.add(event);
			}
		}
		
		return new MouseEventPool(events.toArray(new MouseEvent[events.size()]));
	}
	
	public MouseEventPool getFrom(MouseMotion motion)
	{
		List<MouseEvent> events = new ArrayList<MouseEvent>();
		
		for (MouseEvent event : this)
		{
			if (event != null && event.getMotion() == motion)
			{
				events.add(event);
			}
		}
		
		return new MouseEventPool(events.toArray(new MouseEvent[events.size()]));
	}
	
	public boolean has(MouseMotion motion)
	{
		for (MouseEvent event : this)
		{
			if (event != null && event.getMotion() == motion)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean has(MouseButton button)
	{
		for (MouseEvent event : this)
		{
			if (event != null && event.getButton() == button)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean has(ButtonState state)
	{
		for (MouseEvent event : this)
		{
			if (event != null && event.getState() == state)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Iterator<MouseEvent> iterator()
	{
		return Iterators.forArray(this.events);
	}
	
}