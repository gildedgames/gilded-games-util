package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.event.view.MouseEventGuiFocus;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;

public class Button extends GuiFrame
{

	protected final GuiFrame defaultState, hoveredState, clickedState;

	public Button(Dim2D dim, TextureElement texture)
	{
		this(dim, texture, texture.clone(), texture.clone());
	}

	public Button(Dim2D dim, TextureElement defaultState, TextureElement hoveredState, TextureElement clickedState)
	{
		super(dim);

		Dim2D scissor = Dim2D.build().addModifier(this, ModifierType.ALL).compile();

		this.defaultState = defaultState; //new ScissorableGui(scissor, defaultState);
		this.hoveredState = hoveredState; //new ScissorableGui(scissor, hoveredState);
		this.clickedState = clickedState;// new ScissorableGui(scissor, clickedState);
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);

		this.defaultState.modDim().center(false).resetPos().compile();
		this.hoveredState.modDim().center(false).resetPos().compile();
		this.clickedState.modDim().center(false).resetPos().compile();

		this.clickedState.listeners().setElement("clickEvent", new MouseEventGuiFocus(this.clickedState, new MouseInput(MouseButton.LEFT, ButtonState.PRESSED)));

		this.content().setElement("hoveredState", this.hoveredState);
		this.content().setElement("clickedState", this.clickedState);
		this.content().setElement("defaultState", this.defaultState);
		
		super.initContent(input);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (input.isHovered(this.getDim()))
		{
			this.defaultState.setVisible(false);
			this.hoveredState.setVisible(true);
		}
		else
		{
			this.defaultState.setVisible(true);
			this.hoveredState.setVisible(false);
		}
	}

}
