package com.gildedgames.util.ui;

import com.gildedgames.util.core.gui.util.factory.UIButtonFactoryMC;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.decorators.UIScrollable;
import com.gildedgames.util.ui.util.factory.TestButtonFactory;
import com.gildedgames.util.ui.util.frames.UIButtonList;
import com.gildedgames.util.ui.util.frames.UIScrollBar;
import com.gildedgames.util.ui.util.transform.ViewPositionerButton;

public class TestUI extends UIFrame
{

	public TestUI(UIFrame parent)
	{
		super(parent, new Dimensions2D().setArea(50, 50));
	}

	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);

		Position2D center = new Position2D(input.getScreenWidth() / 2, input.getScreenHeight() / 2);

		UIButtonFactoryMC buttonFactory = new UIButtonFactoryMC();

		UIButtonList list = new UIButtonList(new Position2D(), 60, new ViewPositionerButton(), new TestButtonFactory());

		//list.add(buttonFactory.createButton(new Position2D(), 10, "lol"));
		
		Dimensions2D dim = new Dimensions2D().setArea(60, 200).setPos(new Position2D(0, 10));
		
		container.add(new UIScrollable(dim, list, (UIScrollBar) buttonFactory.createScrollBar(new Position2D(0, 0), 200, dim.clone(), false)));
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		
	}

}
