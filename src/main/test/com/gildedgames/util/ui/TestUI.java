package com.gildedgames.util.ui;

import com.gildedgames.util.core.gui.UIButtonFactoryMC;
import com.gildedgames.util.core.gui.UIFrameMC;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.input.InputProvider;

public class TestUI extends UIFrameMC
{

	public TestUI(UIBase parent)
	{
		super(parent, new Dimensions2D().setArea(50, 50));
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);

		Position2D center = new Position2D(input.getScreenWidth() / 2, input.getScreenHeight() / 2);

		UIButtonFactoryMC buttonFactory = new UIButtonFactoryMC();

		UIBase button = buttonFactory.createArrowButton(center);
		UIBase button2 = buttonFactory.createArrowButton(center.withAdded(-50, -50));
		UIBase button3 = buttonFactory.createButton(center.withAdded(50, 50), 60, "bitch");
		
		container.add(button);
		container.add(button2);
		container.add(button3);
		
		container.add(buttonFactory.createScrollBar(new Position2D(0, 20), 200, false));
	}

}
