package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.event.view.MouseEventViewFocus;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;

public class Button extends AbstractUI
{
	
	protected final TextureElement defaultState, hoveredState, clickedState;

	public Button(Dim2D dim, TextureElement defaultState, TextureElement hoveredState, TextureElement clickedState)
	{
		super(null, dim);
		
		this.defaultState = defaultState;
		this.hoveredState = hoveredState;
		this.clickedState = clickedState;
	}
	
	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);
		
		this.clickedState.getListeners().setElement("clickEvent", new MouseEventViewFocus(this.clickedState, new MouseInput(MouseButton.LEFT, ButtonState.PRESSED)));
		
		container.setElement("defaultState", this.defaultState);
		container.setElement("hoveredState", this.hoveredState);
		container.setElement("clickedState", this.clickedState);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		if (input.isHovered(this.getDim()))
		{
			this.hoveredState.setVisible(true);
		}
		else
		{
			this.hoveredState.setVisible(false);
		}
	}

}
