package com.gildedgames.util.ui.event.view;

import java.util.List;

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
	
	public UIEventViewFocus(UIView view, List<MouseButton> buttons)
	{
		this(view, buttons, null);
	}
	
	public UIEventViewFocus(UIView view, List<MouseButton> buttons, List<ButtonState> states)
	{
		super(buttons, states);
		
		this.view = view;
	}

	@Override
	public void onMouseState(InputProvider input, List<MouseButton> buttons, List<ButtonState> states)
	{
		final boolean buttonCorrect = buttons != null ? buttons.containsAll(this.getButtons()) : true;
		final boolean stateCorrect = buttons != null ? states.containsAll(this.getStates()) : true;
		
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
