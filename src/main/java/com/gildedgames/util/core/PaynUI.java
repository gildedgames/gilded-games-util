package com.gildedgames.util.core;

import java.awt.Color;

import com.gildedgames.util.core.gui.util.UIFactory;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
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
		Dim2D rectangleDim = Dim2D.build().y(20).x(50).width(50).height(2000).compile();

		RectangleElement rectangle = new RectangleElement(rectangleDim, new DrawingData(new Color(403959)), new DrawingData(new Color(0xA30000)));
		
		UIFrame scrollableRectangle = new ScrollableUI(rectangleDim.clone().height(200).width(70).compile(), rectangle, UIFactory.createScrollBar());
		
		this.content().setElement("rectangle", scrollableRectangle);
		
		
	}
	
	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		ScrollableUI rectangle = this.content().getElement("rectangle", ScrollableUI.class);

		if (pool.has(MouseButton.LEFT) && input.isHovered(rectangle))
		{
			
		}
	}

}
