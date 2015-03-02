package com.gildedgames.util.ui;

import com.gildedgames.util.core.gui.UIButtonFactoryMC;
import com.gildedgames.util.core.gui.UIFrameMC;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class TestUI extends UIFrameMC
{

	public TestUI()
	{
		super(new Dimensions2D().setArea(50, 50));
	}
	
	@Override
	public void init(UIElementHolder holder, InputProvider input)
	{
		super.init(holder, input);

		Position2D center = new Position2D(input.getScreenWidth() / 2, input.getScreenHeight() / 2);

		UIButtonFactoryMC buttonFactory = new UIButtonFactoryMC();

		UIBase button = buttonFactory.createArrowButton(center);
		UIBase button2 = buttonFactory.createArrowButton(center.add(-50, -50));
		UIBase button3 = buttonFactory.createButton(center.add(50, 50), 60, "bitch");
		
		holder.add(button);
		holder.add(button2);
		holder.add(button3);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	@Override
	public void onFocused(InputProvider input)
	{
		
	}

}
