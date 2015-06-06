package com.gildedgames.util.ui.common;

import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public interface UIView extends UIElement, Dim2DHolder
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