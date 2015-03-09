package com.gildedgames.util.ui.event;

import java.util.List;

import scala.actors.threadpool.Arrays;

import com.gildedgames.util.ui.input.MouseEvent;
import com.gildedgames.util.ui.listeners.IMouseListener;

public abstract class UIEventMouse extends UIEvent implements IMouseListener
{
	
	private final MouseEvent[] events;
	
	private final List<MouseEvent> eventList;
	
	public UIEventMouse(MouseEvent... events)
	{
		this.events = events;
		this.eventList = Arrays.asList(this.events);
	}
	
	public UIEventMouse(List<MouseEvent> events)
	{
		this.eventList = events;
		this.events = this.eventList.toArray(new MouseEvent[this.eventList.size()]);
	}
	
	public MouseEvent[] getEventArray()
	{
		return this.events;
	}

	public List<MouseEvent> getEvents()
	{
		return this.eventList;
	}
	
}