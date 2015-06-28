package com.gildedgames.util.core;

import java.awt.Color;

import com.gildedgames.util.core.gui.util.UIFactory;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.decorators.ScrollableUI;

public class PaynUI extends UIFrame
{

	public PaynUI()
	{
		super(Dim2D.compile());
	}
	
	@Override
	public void init(InputProvider input)
	{
		Dim2D rectangleDim = Dim2D.build().y(20).x(20).width(50).height(2000).compile();
		
		RectangleElement rectangle = new RectangleElement(rectangleDim, new DrawingData(new Color(403959)));
		
		UIFrame scrollableRectangle = new ScrollableUI(Dim2D.build().x(20).y(20).width(50).height(200).compile(), rectangle, UIFactory.createScrollBar());
		
		this.content().setElement("rectangle", scrollableRectangle);
		this.content().setElement("redRectangle", new RectangleElement(Dim2D.build().y(60).x(60).width(20).height(60).compile(), new DrawingData(new Color(0xE32015))));
	}

}
