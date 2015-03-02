package com.gildedgames.util.ui.graphics;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Position2D;



public interface IGraphics
{

	void drawSprite(Sprite sprite, Dimensions2D dim, DrawingData data);
	
	void drawText(Text text, Dimensions2D dim, DrawingData data);

	void drawLine(Position2D startPos, Position2D endPos, DrawingData drawingData);

	void drawRectangle(Dimensions2D dim, DrawingData data);
	
	void drawGradientRectangle(Dimensions2D dim, DrawingData startColor, DrawingData endColor);
	
}