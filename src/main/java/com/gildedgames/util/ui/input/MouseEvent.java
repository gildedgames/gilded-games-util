package com.gildedgames.util.ui.input;


public class MouseEvent extends ButtonEvent
{

	protected MouseButton button;
	
	protected MouseMotion motion;
	
	public MouseEvent(MouseButton button)
	{
		this(button, ButtonState.PRESSED);
	}
	
	public MouseEvent(MouseButton button, ButtonState state)
	{
		this(button, state, MouseMotion.STILL);
	}
	
	public MouseEvent(MouseButton button, ButtonState state, MouseMotion motion)
	{
		super(state);
		
		this.button = button;
		this.motion = motion;
	}
	
	public MouseButton getButton()
	{
		return this.button;
	}
	
	public MouseMotion getMotion()
	{
		return this.motion;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof MouseEvent)
		{
			MouseEvent event = (MouseEvent)obj;
			
			if (event.getButton() == this.getButton() && event.getMotion() == this.getMotion())
			{
				return super.equals(obj);
			}
		}
		
		return false;
	}
	
}
