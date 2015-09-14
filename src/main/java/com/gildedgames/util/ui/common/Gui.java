package com.gildedgames.util.ui.common;

import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public interface Gui extends Ui, Dim2DHolder
{

	void draw(Graphics2D graphics, InputProvider input);
	
	/**
	 * There are 20 ticks in a second.
	 * @return
	 */
	int ticksClosing();
	
	/**
	 * There are 20 ticks in a second.
	 * @return
	 */
	int ticksOpening();

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