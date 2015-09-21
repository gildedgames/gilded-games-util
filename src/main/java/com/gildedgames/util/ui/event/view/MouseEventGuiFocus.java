package com.gildedgames.util.ui.event.view;

import java.util.List;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MouseEventGuiFocus extends MouseEventGui
{

	public MouseEventGuiFocus(Gui view)
	{
		super(view);
	}

	public MouseEventGuiFocus(Gui view, List<MouseInput> events)
	{
		super(view, events);
	}

	public MouseEventGuiFocus(Gui view, MouseInput... events)
	{
		super(view, events);
	}

	@Override
	protected void onTrue(InputProvider input, MouseInputPool pool)
	{
		this.gui.setVisible(true);
	}

	@Override
	protected void onFalse(InputProvider input, MouseInputPool pool)
	{
		this.gui.setVisible(false);
	}

}
