package com.gildedgames.util.ui.event.view;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;

public abstract class MouseEventView extends MouseEvent
{
	
	protected final UIView view;

	public MouseEventView(UIView view)
	{
		this(view, new ArrayList<MouseInput>());
	}
	
	public MouseEventView(UIView view, List<MouseInput> events)
	{
		super(events);
		
		this.view = view;
	}
	
	public MouseEventView(UIView view, MouseInput... events)
	{
		super(events);
		
		this.view = view;
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
