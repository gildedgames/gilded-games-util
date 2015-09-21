package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;

public class GuiFrameEvent extends GuiFrame
{

	private boolean enabled = true;
	
	public GuiFrameEvent()
	{
		super(null);
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
	public Dim2D getDim()
	{
		return null;
	}

	@Override
	public void initContent(InputProvider input)
	{
		
	}
	
	@Override
	public void onResolutionChange(InputProvider input)
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
	public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
	{
		return false;
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{

	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
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
	public UIContainerMutable listeners()
	{
		return null;
	}
	
}
