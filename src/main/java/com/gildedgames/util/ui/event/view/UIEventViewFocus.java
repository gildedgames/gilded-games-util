package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.event.UIEventMouse;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseEvent;
import com.gildedgames.util.ui.input.MouseEventPool;

public class UIEventViewFocus extends UIEventMouse
{
	
	protected final UIView view;

	public UIEventViewFocus(UIView view)
	{
		this(view, new ArrayList<MouseEvent>());
	}
	
	public UIEventViewFocus(UIView view, List<MouseEvent> events)
	{
		super(events);
		
		this.view = view;
	}
	
	public UIEventViewFocus(UIView view, MouseEvent... events)
	{
		super(events);
		
		this.view = view;
	}

	@Override
	public void onMouseEvent(InputProvider input, MouseEventPool pool)
	{
		if (input.isHovered(this.view.getDimensions()) && pool.containsAll(this.getEvents()))
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
