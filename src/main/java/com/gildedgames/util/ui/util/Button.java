package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.event.view.MouseEventGuiFocus;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.util.decorators.ScissorableGui;

public class Button extends GuiFrame
{

	protected final GuiFrame defaultState, hoveredState, clickedState;

	public Button(Dim2D dim, TextureElement texture)
	{
		this(dim, texture, texture, texture);
	}

	public Button(Dim2D dim, TextureElement defaultState, TextureElement hoveredState, TextureElement clickedState)
	{
		super(dim);

		this.defaultState = new ScissorableGui(dim, defaultState);
		this.hoveredState = new ScissorableGui(dim, hoveredState);
		this.clickedState = new ScissorableGui(dim, clickedState);
	}

	@Override
	public void init(InputProvider input)
	{
		super.init(input);

		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);

		this.defaultState.modDim().center(false).resetPos().compile();
		this.hoveredState.modDim().center(false).resetPos().compile();
		this.clickedState.modDim().center(false).resetPos().compile();

		this.clickedState.listeners().setElement("clickEvent", new MouseEventGuiFocus(this.clickedState, new MouseInput(MouseButton.LEFT, ButtonState.PRESSED)));

		this.content().setElement("defaultState", this.defaultState);
		this.content().setElement("hoveredState", this.hoveredState);
		this.content().setElement("clickedState", this.clickedState);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (input.isHovered(this.getDim()))
		{
			for (Gui element : ObjectFilter.getTypesFrom(this.content(), Gui.class))
			{
				element.setVisible(false);
			}
			
			this.hoveredState.setVisible(true);
		}
		else
		{
			for (Gui element : ObjectFilter.getTypesFrom(this.content(), Gui.class))
			{
				element.setVisible(true);
			}
			
			this.hoveredState.setVisible(false);
		}
	}

}
