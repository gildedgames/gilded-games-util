package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.input.InputProvider;

public class ScreenUtil
{

	public static Pos2D getCenter(InputProvider input)
	{
		return new Pos2D(input.getScreenWidth() / 2, input.getScreenHeight() / 2);
	}
	
	public static Pos2D getBottomCenter(InputProvider input)
	{
		return ScreenUtil.getCenter(input).addY(input.getScreenHeight() / 2);
	}
	
	public static Pos2D getBottomRight(InputProvider input)
	{
		return ScreenUtil.getBottomCenter(input).addX(input.getScreenWidth() / 2);
	}
	
	public static Pos2D getBottomLeft(InputProvider input)
	{
		return ScreenUtil.getBottomRight(input).addX(-input.getScreenWidth());
	}
	
	public static Pos2D getCenterLeft(InputProvider input)
	{
		return ScreenUtil.getCenter(input).addX(-input.getScreenWidth() / 2);
	}
	
	public static Pos2D getCenterRight(InputProvider input)
	{
		return ScreenUtil.getCenterLeft(input).addX(input.getScreenWidth());
	}
	
	public static Pos2D getTopCenter(InputProvider input)
	{
		return ScreenUtil.getCenter(input).addY(-input.getScreenHeight() / 2);
	}
	
	public static Pos2D getTopLeft(InputProvider input)
	{
		return ScreenUtil.getTopCenter(input).addX(-input.getScreenWidth() / 2);
	}
	
	public static Pos2D getTopRight(InputProvider input)
	{
		return ScreenUtil.getTopLeft(input).addX(input.getScreenWidth());
	}
	
}
