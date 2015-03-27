package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;

public class FrameEvent extends UIFrame
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
	public Dimensions2D getDimensions()
	{
		return null;
	}

	@Override
	public void setDimensions(Dimensions2D dim)
	{
		
	}

	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(UIContainer container, InputProvider input)
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
	public UIContainer getListeners()
	{
		return null;
	}
	
	@Override
	public UIFrame getPreviousFrame()
	{
		return null;
	}
	
}
