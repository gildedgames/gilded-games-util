package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.ObjectFilter;

public abstract class UIBasicAbstract implements UIBasic
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();

	private boolean visible = true, enabled = true, focused = false;

	private UIContainer listeners = new UIContainer();
	
	private UIBasicAbstract previousFrame;
	
	private Dimensions2D dimensions;
	
	public UIBasicAbstract(Dimensions2D dimensions)
	{
		this(null, dimensions);
	}

	public UIBasicAbstract(UIBasicAbstract previousFrame, Dimensions2D dimensions)
	{
		this.previousFrame = previousFrame;
		this.dimensions = dimensions;
	}
	
	@Override
	public UIContainer getListeners()
	{
		return this.listeners;
	}

	@Override
	public UIBasicAbstract getPreviousFrame()
	{
		return this.previousFrame;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
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
	public Dimensions2D getDimensions()
	{
		return this.dimensions;
	}

	@Override
	public void setDimensions(Dimensions2D dimensions)
	{
		this.dimensions = dimensions;
	}

	@Override
	public boolean isFocused()
	{
		return this.focused;
	}

	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	@Override
	public boolean query(Object... input)
	{
		return false;
	}
	
	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return false;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		
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
	public void onInit(UIContainer container, InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(UIContainer container, InputProvider input)
	{
		this.onInit(container, input);
	}

}