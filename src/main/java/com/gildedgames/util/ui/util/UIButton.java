package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.event.view.MouseEventViewFocus;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;

public class UIButton extends UIBasicAbstract
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
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);
		
		this.clickedState.getListeners().add(new MouseEventViewFocus(this.clickedState, new MouseInput(MouseButton.LEFT, ButtonState.PRESSED)));
		
		container.add(this.defaultState);
		container.add(this.hoveredState);
		container.add(this.clickedState);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
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
