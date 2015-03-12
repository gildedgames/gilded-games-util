package com.gildedgames.util.ui;

import java.util.List;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public interface UIView extends UIElement
{

	void draw(IGraphics graphics, InputProvider input);

	boolean isVisible();

	void setVisible(boolean visible);

	Dimensions2D getDimensions();

	void setDimensions(Dimensions2D dim);
	
	boolean isFocused();
	
	void setFocused(boolean focused);
	
	boolean query(List input);

}