package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.view.MouseEventGuiFocus;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;

public class Button extends GuiFrame
{

	protected final GuiFrame defaultState, hoveredState, clickedState;

	public Button(Rect dim, TextureElement texture)
	{
		this(dim, texture, texture.clone(), texture.clone());
	}

	public Button(Rect dim, TextureElement defaultState, TextureElement hoveredState, TextureElement clickedState)
	{
		super(dim);

		Rect scissor = Dim2D.build().addModifier(this, ModifierType.ALL).flush();

		this.defaultState = defaultState;//new ScissorableGui(scissor, defaultState);
		this.hoveredState = hoveredState;//new ScissorableGui(scissor, hoveredState);
		this.clickedState = clickedState;// new ScissorableGui(scissor, clickedState);
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.defaultState.setVisible(true);
		this.hoveredState.setVisible(false);
		this.clickedState.setVisible(false);

		this.defaultState.dim().mod().center(false).resetPos().flush();
		this.hoveredState.dim().mod().center(false).resetPos().flush();
		this.clickedState.dim().mod().center(false).resetPos().flush();

		this.clickedState.events().set("clickEvent", new MouseEventGuiFocus(new MouseInput(MouseButton.LEFT, ButtonState.PRESSED)));

		this.content().set("hoveredState", this.hoveredState);
		this.content().set("clickedState", this.clickedState);
		this.content().set("defaultState", this.defaultState);
		
		super.initContent(input);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);

		if (input.isHovered(this.dim()))
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
