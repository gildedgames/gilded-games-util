package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.DimensionsHolder;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public interface UIView extends UIElement, DimensionsHolder
{

	void draw(Graphics2D graphics, InputProvider input);

	boolean isVisible();

	void setVisible(boolean visible);
	
	boolean isFocused();
	
	void setFocused(boolean focused);
	
	/**
	 * @param input
	 * @return True if this object meets the criteria passed through the input
	 */
	boolean query(Object... input);

}