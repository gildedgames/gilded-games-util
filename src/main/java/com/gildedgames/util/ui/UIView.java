package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;

public interface UIView extends UIElement
{

	void draw(IGraphics graphics);

	boolean isVisible();

	void setVisible(boolean visible);

	Dimensions2D getFocusArea();

	void setFocusArea(Dimensions2D focusArea);
	
	void onFocused();
	
}