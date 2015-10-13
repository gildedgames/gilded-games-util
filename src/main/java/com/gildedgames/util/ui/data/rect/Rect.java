package com.gildedgames.util.ui.data.rect;

import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.Rotation2D;
import com.gildedgames.util.ui.input.InputProvider;

public interface Rect
{
	
	RectBuilder rebuild();

	Rotation2D rotation();

	float degrees();

	Pos2D origin();

	float scale();

	Pos2D pos();

	Pos2D maxPos();

	float maxX();

	float maxY();

	float x();

	float y();

	float width();

	float height();

	boolean isCenteredX();

	boolean isCenteredY();

	boolean intersects(Pos2D pos);

	boolean intersects(Rect dim);
	
	boolean isHovered(InputProvider input);

}
