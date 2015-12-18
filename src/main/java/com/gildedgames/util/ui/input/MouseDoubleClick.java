package com.gildedgames.util.ui.input;

public class MouseDoubleClick implements MouseInputBehavior
{
	
	private MouseInput[] events;
	
	private final int msRequired = 300;
	
	private long lastClickTime;
	
	private MouseDoubleClick(MouseInput... events)
	{
		this.events = events;
	}

	@Override
	public boolean isMet(InputProvider input, MouseInputPool pool, int scrollDifference)
	{
		boolean doubleClicked = System.currentTimeMillis() - this.lastClickTime <= this.msRequired;
		
		this.lastClickTime = System.currentTimeMillis();
		
		return doubleClicked && pool.containsAll(this.events);
	}
	
	public static MouseDoubleClick with(MouseInput... events)
	{
		return new MouseDoubleClick(events);
	}

}
