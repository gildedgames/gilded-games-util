package com.gildedgames.util.ui.input;


public class ButtonEvent
{

	protected ButtonState state;
	
	public ButtonEvent(ButtonState state)
	{
		this.state = state;
	}
	
	public ButtonState getState()
	{
		return this.state;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ButtonEvent)
		{
			ButtonEvent event = (ButtonEvent)obj;
			
			if (event.getState() == this.getState())
			{
				return true;
			}
		}
		
		return false;
	}
	
}
