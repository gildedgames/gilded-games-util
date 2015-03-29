package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public interface UIView extends UIElement
{

	void draw(Graphics2D graphics, InputProvider input);

	boolean isVisible();

	void setVisible(boolean visible);

	Dimensions2D getDimensions();

	void setDimensions(Dimensions2D dim);
	
	boolean isFocused();
	
	void setFocused(boolean focused);
	
	/**
	 * @param input
	 * @return True if this object meets the criteria passed through the input
	 */
	boolean query(Object... input);

}