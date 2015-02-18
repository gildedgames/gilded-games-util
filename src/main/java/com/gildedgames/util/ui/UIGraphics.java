package com.gildedgames.util.ui;

public interface UIGraphics<GRAPHICS> extends UIDimensions, UIElement
{

	void draw(GRAPHICS graphics);
	
	boolean isGraphicsCompatible(Object graphics);
	
	boolean isVisible();
	
	void setVisible(boolean visible);
	
}
