package com.gildedgames.util.modules.ui.event.view;

import java.util.List;

import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.input.MouseInput;
import com.gildedgames.util.modules.ui.input.MouseInputPool;

public class MouseEventGuiFocus extends MouseEventGui
{

	public MouseEventGuiFocus()
	{
		super();
	}

	public MouseEventGuiFocus(List<MouseInput> events)
	{
		super(events);
	}

	public MouseEventGuiFocus(MouseInput... events)
	{
		super(events);
	}

	@Override
	protected void onTrue(InputProvider input, MouseInputPool pool)
	{
		this.getGui().setVisible(true);
	}

	@Override
	protected void onFalse(InputProvider input, MouseInputPool pool)
	{
		this.getGui().setVisible(false);
	}

	@Override
	public void initEvent()
	{
		
	}

}
