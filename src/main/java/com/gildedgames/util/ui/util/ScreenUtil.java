package com.gildedgames.util.ui.util;

import org.lwjgl.opengl.Display;

import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.input.InputProvider;

public class ScreenUtil
{
	
	public static Pos2D convertToOpenGL(InputProvider input, Pos2D pos)
	{
		float heightScaleFactor = Display.getHeight() / input.getScreenHeight();
		float widthScaleFactor = Display.getWidth() / input.getScreenWidth();
		
		return Pos2D.flush((int)(pos.x() * widthScaleFactor), (int)(pos.y() * heightScaleFactor));
	}

	public static Pos2D getCenter(InputProvider input)
	{
		return Pos2D.flush(input.getScreenWidth() / 2, input.getScreenHeight() / 2);
	}
	
	public static Pos2D getBottomCenter(InputProvider input)
	{
		return ScreenUtil.getCenter(input).clone().addY(input.getScreenHeight() / 2).flush();
	}
	
	public static Pos2D getBottomRight(InputProvider input)
	{
		return ScreenUtil.getBottomCenter(input).clone().addX(input.getScreenWidth() / 2).flush();
	}
	
	public static Pos2D getBottomLeft(InputProvider input)
	{
		return ScreenUtil.getBottomRight(input).clone().addX(-input.getScreenWidth()).flush();
	}
	
	public static Pos2D getCenterLeft(InputProvider input)
	{
		return ScreenUtil.getCenter(input).clone().addX(-input.getScreenWidth() / 2).flush();
	}
	
	public static Pos2D getCenterRight(InputProvider input)
	{
		return ScreenUtil.getCenterLeft(input).clone().addX(input.getScreenWidth()).flush();
	}
	
	public static Pos2D getTopCenter(InputProvider input)
	{
		return ScreenUtil.getCenter(input).clone().addY(-input.getScreenHeight() / 2).flush();
	}
	
	public static Pos2D getTopLeft(InputProvider input)
	{
		return ScreenUtil.getTopCenter(input).clone().addX(-input.getScreenWidth() / 2).flush();
	}
	
	public static Pos2D getTopRight(InputProvider input)
	{
		return ScreenUtil.getTopLeft(input).clone().addX(input.getScreenWidth()).flush();
	}
	
}
