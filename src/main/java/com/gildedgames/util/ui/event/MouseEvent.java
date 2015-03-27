package com.gildedgames.util.ui.event;

import java.util.List;

import scala.actors.threadpool.Arrays;

import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.listeners.MouseListener;

public abstract class MouseEvent extends ElementEvent implements MouseListener
{
	
	private final MouseInput[] input;
	
	private final List<MouseInput> eventList;
	
	public MouseEvent(MouseInput... events)
	{
		this.input = events;
		this.eventList = Arrays.asList(this.input);
	}
	
	public MouseEvent(List<MouseInput> events)
	{
		this.eventList = events;
		this.input = this.eventList.toArray(new MouseInput[this.eventList.size()]);
	}
	
	public MouseInput[] getEventArray()
	{
		return this.input;
	}

	public List<MouseInput> getEvents()
	{
		return this.eventList;
	}
	
}