package com.gildedgames.util.modules.ui.util;

import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.Pos2D;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.input.InputProvider;
import org.lwjgl.opengl.Display;

import java.util.List;

public class InputHelper
{

	public static float prevMouseX, prevMouseY;

	public static boolean prevResult;

	public interface InputCondition
	{

		boolean isMet(Gui gui);

	}

	public static boolean isHovered(final InputCondition condition, final InputProvider input)
	{
		if (prevMouseX != input.getMouseX() || prevMouseY != input.getMouseY())
		{
			prevMouseX = input.getMouseX();
			prevMouseY = input.getMouseY();

			if (UiModule.locate().hasFrame())
			{
				GuiFrame frame = UiModule.locate().getCurrentFrame();

				List<Gui> guis = frame.seekContent().queryAll();

				guis.addAll(frame.events().queryAll(input));

				for (Gui gui : guis)
				{
					if (condition.isMet(gui) && input.isHovered(gui.dim()))
					{
						prevResult = true;

						return true;
					}
				}
			}

			prevResult = false;

			return false;
		}

		return prevResult;
	}

	public static boolean isInsideScreen(InputProvider input, Rect rect)
	{
		return !(rect.maxX() > input.getScreenWidth() || rect.maxY() > input.getScreenHeight());
	}

	public static Pos2D convertToOpenGL(InputProvider input, Pos2D pos)
	{
		double heightScaleFactor = Display.getHeight() / input.getScreenHeight();
		double widthScaleFactor = Display.getWidth() / input.getScreenWidth();

		return Pos2D.flush((int) (pos.x() * widthScaleFactor), (int) (pos.y() * heightScaleFactor));
	}

	public static Pos2D getCenter(InputProvider input)
	{
		return Pos2D.flush(input.getScreenWidth() / 2, input.getScreenHeight() / 2);
	}

	public static Pos2D getBottomCenter(InputProvider input)
	{
		return InputHelper.getCenter(input).clone().addY(input.getScreenHeight() / 2).flush();
	}

	public static Pos2D getBottomRight(InputProvider input)
	{
		return InputHelper.getBottomCenter(input).clone().addX(input.getScreenWidth() / 2).flush();
	}

	public static Pos2D getBottomLeft(InputProvider input)
	{
		return InputHelper.getBottomRight(input).clone().addX(-input.getScreenWidth()).flush();
	}

	public static Pos2D getCenterLeft(InputProvider input)
	{
		return InputHelper.getCenter(input).clone().addX(-input.getScreenWidth() / 2).flush();
	}

	public static Pos2D getCenterRight(InputProvider input)
	{
		return InputHelper.getCenterLeft(input).clone().addX(input.getScreenWidth()).flush();
	}

	public static Pos2D getTopCenter(InputProvider input)
	{
		return InputHelper.getCenter(input).clone().addY(-input.getScreenHeight() / 2).flush();
	}

	public static Pos2D getTopLeft(InputProvider input)
	{
		return InputHelper.getTopCenter(input).clone().addX(-input.getScreenWidth() / 2).flush();
	}

	public static Pos2D getTopRight(InputProvider input)
	{
		return InputHelper.getTopLeft(input).clone().addX(input.getScreenWidth()).flush();
	}

}
