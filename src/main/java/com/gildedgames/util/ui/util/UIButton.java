package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.event.view.UIEventViewFocus;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIButton extends UIFrame
{
	
	protected final UITexture defaultState, hoveredState, clickedState;

	public UIButton(Dimensions2D dim, UITexture defaultState, UITexture hoveredState, UITexture clickedState)
	{
		super(dim);
		
		this.defaultState = defaultState;
		this.hoveredState = hoveredState;
		this.clickedState = clickedState;
	}
	
	@Override
	public void init(UIElementHolder holder, Dimensions2D screenDimensions)
	{
		super.init(holder, screenDimensions);
		
		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);
		
		this.clickedState.add(new UIEventViewFocus(this.clickedState, MouseButton.LEFT, ButtonState.PRESS));
		
		holder.add(this.defaultState);
		holder.add(this.hoveredState);
		holder.add(this.clickedState);
		
		this.defaultState.setFocusArea(this.getFocusArea());
		this.hoveredState.setFocusArea(this.getFocusArea());
		this.clickedState.setFocusArea(this.getFocusArea());
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		if (input.isHovered(this.getFocusArea()))
		{
			this.hoveredState.setVisible(true);
		}
		else
		{
			this.hoveredState.setVisible(false);
		}
	}

	@Override
	public void onFocused(InputProvider input)
	{
		super.onFocused(input);
	}

}
