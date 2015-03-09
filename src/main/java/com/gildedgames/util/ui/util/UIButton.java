package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.event.view.UIEventViewFocus;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseEvent;

public class UIButton extends UIFrame
{
	
	protected final UITexture defaultState, hoveredState, clickedState;

	public UIButton(Dimensions2D dim, UITexture defaultState, UITexture hoveredState, UITexture clickedState)
	{
		super(null, dim);
		
		this.defaultState = defaultState;
		this.hoveredState = hoveredState;
		this.clickedState = clickedState;
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);
		
		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);
		
		this.clickedState.add(new UIEventViewFocus(this.clickedState, new MouseEvent(MouseButton.LEFT, ButtonState.PRESSED)));
		
		container.add(this.defaultState);
		container.add(this.hoveredState);
		container.add(this.clickedState);
		
		this.defaultState.setDimensions(this.getDimensions());
		this.hoveredState.setDimensions(this.getDimensions());
		this.clickedState.setDimensions(this.getDimensions());
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		if (input.isHovered(this.getDimensions()))
		{
			this.hoveredState.setVisible(true);
		}
		else
		{
			this.hoveredState.setVisible(false);
		}
	}

}
