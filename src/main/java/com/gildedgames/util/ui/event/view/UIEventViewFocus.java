package com.gildedgames.util.ui.event.view;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.event.UIEventMouse;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIEventViewFocus extends UIEventMouse
{
	
	protected final UIView view;

	public UIEventViewFocus(UIView view)
	{
		this(view, null, null);
	}
	
	public UIEventViewFocus(UIView view, MouseButton button)
	{
		this(view, button, null);
	}
	
	public UIEventViewFocus(UIView view, MouseButton button, ButtonState state)
	{
		super(button, state);
		
		this.view = view;
	}

	@Override
	public void onMouseState(InputProvider input, MouseButton button, ButtonState state)
	{
		final boolean buttonCorrect = button != null ? button.equals(this.getButton()) : true;
		final boolean stateCorrect = button != null ? state.equals(this.getState()) : true;
		
		if (input.isHovered(this.view.getDimensions()) && buttonCorrect && stateCorrect)
		{
			this.view.setVisible(true);
		}
		else
		{
			this.view.setVisible(false);
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		
	}
	
	public UIView getView()
	{
		return this.view;
	}

}
