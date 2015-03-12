package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.gui.UIButtonFactoryMC;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Position2D;

public class ButtonFiller implements IContentFiller
{

	@Override
	public List<UIView> fillContent()
	{
		List<UIView> buttons = new ArrayList<UIView>();
		
		UIButtonFactoryMC factory = new UIButtonFactoryMC();
		
		for (int count = 0; count < 100; count++)
		{
			buttons.add(factory.createButton(new Position2D(), 60, "Button " + (count + 1), false));
		}
		
		return buttons;
	}

}
