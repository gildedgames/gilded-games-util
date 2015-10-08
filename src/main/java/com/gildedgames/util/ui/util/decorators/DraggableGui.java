package com.gildedgames.util.ui.util.decorators;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;


public class DraggableGui extends GuiFrame
{
	
	private Gui element;

	public DraggableGui(Gui element)
	{
		this.element = element;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.content().set("element", this.element);
	}
	
	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		super.onMouseInput(pool, input);

		if (pool.has(MouseButton.LEFT))
		{
			if (pool.has(ButtonState.PRESSED))
			{
				
			}
		}
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		this.modDim().pos(Pos2D.flush(input.getMouseX(), input.getMouseY())).flush();
	}

}
