package com.gildedgames.util.ui.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputBehavior;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.MouseListener;

public abstract class MouseEvent extends UiEvent implements MouseListener
{

	private final Collection<MouseInput> eventList;
	
	private final List<MouseInputBehavior> behaviors = new ArrayList<MouseInputBehavior>();
	
	protected int scrollDifference;
	
	public MouseEvent(MouseInput... events)
	{
		super();
		
		this.eventList = Arrays.<MouseInput>asList(events);
	}
	
	public MouseEvent(List<MouseInput> events)
	{
		super();
		
		this.eventList = events;
	}
	
	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		this.scrollDifference = scrollDifference;
	}

	public Collection<MouseInput> getEvents()
	{
		return this.eventList;
	}
	
	public Collection<MouseInputBehavior> getBehaviors()
	{
		return this.behaviors;
	}
	
	public void addBehavior(MouseInputBehavior behavior)
	{
		this.behaviors.add(behavior);
	}
	
	public boolean behaviorsMet(InputProvider input, MouseInputPool pool, int scrollDifference)
	{
		for (MouseInputBehavior behavior : this.behaviors)
		{
			if (!behavior.isMet(input, pool, scrollDifference))
			{
				return false;
			}
		}
		
		return true;
	}
	
}