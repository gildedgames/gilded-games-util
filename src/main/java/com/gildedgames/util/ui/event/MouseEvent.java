package com.gildedgames.util.ui.event;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.listeners.MouseListener;

public abstract class MouseEvent extends UiEvent implements MouseListener
{

	private final Collection<MouseInput> eventList;
	
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

	public Collection<MouseInput> getEvents()
	{
		return this.eventList;
	}
	
}