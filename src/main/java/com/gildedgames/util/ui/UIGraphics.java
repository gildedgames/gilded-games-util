package com.gildedgames.util.ui;

import com.gildedgames.util.ui.util.UIDimensions;

public interface UIGraphics<GRAPHICS> extends UIElement
{

	void draw(GRAPHICS graphics);

	Class<? extends GRAPHICS> getGraphicsClass();

	boolean isVisible();

	void setVisible(boolean visible);

	UIDimensions getDimensions();

	void setDimensions(UIDimensions dimensions);

}
