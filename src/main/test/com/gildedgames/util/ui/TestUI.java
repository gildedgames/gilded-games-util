package com.gildedgames.util.ui;

import com.gildedgames.util.core.gui.UIButtonFactoryMC;
import com.gildedgames.util.core.gui.UIFrameMC;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.TestButtonProvider;
import com.gildedgames.util.ui.util.UIButtonList;
import com.gildedgames.util.ui.util.UIScrollBar;
import com.gildedgames.util.ui.util.UIScrollable;
import com.gildedgames.util.ui.util.ViewPositionerButton;

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

		UIButtonList list = new UIButtonList(new Position2D(), 60, new ViewPositionerButton(), new TestButtonProvider());

		//list.add(buttonFactory.createButton(new Position2D(), 10, "lol"));
		
		container.add(new UIScrollable(new Dimensions2D().setArea(60, 200), list, (UIScrollBar) buttonFactory.createScrollBar(new Position2D(0, 0), 200, false)));
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		
	}

}
