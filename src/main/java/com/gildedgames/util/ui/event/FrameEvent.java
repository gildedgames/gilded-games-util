package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;

public class FrameEvent extends AbstractUI
{

	private boolean enabled = true;
	
	public FrameEvent()
	{
		super(null, null);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{

	}

	@Override
	public boolean isVisible()
	{
		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{

	}

	@Override
	public Dim2D getDimensions()
	{
		return null;
	}

	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(UIElementContainer container, InputProvider input)
	{
		
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return false;
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{

	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		
	}

	@Override
	public boolean isFocused()
	{
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		
	}
	
	@Override
	public boolean query(Object... input)
	{
		return false;
	}

	@Override
	public UIElementContainer getListeners()
	{
		return null;
	}
	
	@Override
	public AbstractUI getPreviousFrame()
	{
		return null;
	}
	
}
