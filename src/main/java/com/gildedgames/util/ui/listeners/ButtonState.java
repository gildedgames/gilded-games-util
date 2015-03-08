package com.gildedgames.util.ui.listeners;

public enum ButtonState
{

	NONE(-1), DOWN(0), PRESS(1), RELEASED(2), MOVING(3), STILL(4);
	
	private final int index;
	
	ButtonState(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public static ButtonState fromIndex(int index)
	{
		for (ButtonState button : values())
		{
			if (button.getIndex() == index)
			{
				return button;
			}
		}
		
		return NONE;
	}
	
}
