package com.gildedgames.util.ui.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Iterators;

public class KeyEventPool implements Iterable<KeyEvent>
{
	
	protected final KeyEvent[] events;

	public KeyEventPool(KeyEvent... events)
	{
		this.events = events;
	}
	
	public int size()
	{
		return this.events.length;
	}
	
	public KeyEvent get(int index)
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
	
	public KeyEventPool getFrom(int key)
	{
		List<KeyEvent> events = new ArrayList<KeyEvent>();
		
		for (KeyEvent event : this)
		{
			if (event != null && event.getKey() == key)
			{
				events.add(event);
			}
		}
		
		return new KeyEventPool(events.toArray(new KeyEvent[events.size()]));
	}
	
	public KeyEventPool getFrom(char character)
	{
		List<KeyEvent> events = new ArrayList<KeyEvent>();
		
		for (KeyEvent event : this)
		{
			if (event != null && event.getChar() == character)
			{
				events.add(event);
			}
		}
		
		return new KeyEventPool(events.toArray(new KeyEvent[events.size()]));
	}
	
	public KeyEventPool getFrom(ButtonState state)
	{
		List<KeyEvent> events = new ArrayList<KeyEvent>();
		
		for (KeyEvent event : this)
		{
			if (event != null && event.getState() == state)
			{
				events.add(event);
			}
		}
		
		return new KeyEventPool(events.toArray(new KeyEvent[events.size()]));
	}
	
	public boolean has(int key)
	{
		for (KeyEvent event : this)
		{
			if (event != null && event.getKey() == key)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean has(char character)
	{
		for (KeyEvent event : this)
		{
			if (event != null && event.getChar() == character)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean has(ButtonState state)
	{
		for (KeyEvent event : this)
		{
			if (event != null && event.getState() == state)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Iterator<KeyEvent> iterator()
	{
		return Iterators.forArray(this.events);
	}
	
}
