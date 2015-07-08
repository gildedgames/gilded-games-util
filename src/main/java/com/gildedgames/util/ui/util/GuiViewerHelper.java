package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UiContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;

public final class GuiViewerHelper
{

	public static void processInitPre(InputProvider input, UiContainer... containers)
	{
		for (UiContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				Dim2DHolder parentModifier = ObjectFilter.getType(element, Dim2DHolder.class);

				element.init(input);

				if (parentModifier != null)
				{
					for (Ui child : element.seekContent())
					{
						Dim2DHolder dimHolder = ObjectFilter.getType(child, Dim2DHolder.class);

						if (dimHolder != null)
						{
							dimHolder.modDim().addModifier(parentModifier, ModifierType.POS, ModifierType.SCALE).compile();
						}
					}
				}
			}
		}
	}

	public static void processResolutionChange(InputProvider input, UiContainer... containers)
	{
		GuiViewerHelper.processInitPre(input, containers);
	}

	public static void processMouseInput(InputProvider input, MouseInputPool pool, UiContainer... containers)
	{
		for (UiContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (MouseListener element : ObjectFilter.getTypesFrom(container.elements(), MouseListener.class))
			{
				if (element.isEnabled())
				{
					element.onMouseInput(input, pool);
				}
			}
		}
	}

	public static void processMouseScroll(InputProvider input, int scrollDifference, UiContainer... containers)
	{
		for (UiContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (MouseListener element : ObjectFilter.getTypesFrom(container.elements(), MouseListener.class))
			{
				if (element.isEnabled())
				{
					element.onMouseScroll(input, scrollDifference);
				}
			}
		}
	}

	public static boolean processKeyboardInput(KeyboardInputPool pool, UiContainer... containers)
	{
		boolean success = false;
		
		for (UiContainer container : containers)
		{
			if (container == null)
			{
				return false;
			}

			for (KeyboardListener element : ObjectFilter.getTypesFrom(container.elements(), KeyboardListener.class))
			{
				if (element.isEnabled())
				{
					GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);

					success = element.onKeyboardInput(pool) || success;
				}
			}
		}

		return success;
	}

	public static void processDraw(Graphics2D graphics, InputProvider input, UiContainer... containers)
	{
		for (UiContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Gui element : ObjectFilter.getTypesFrom(container.elements(), Gui.class))
			{
				if (element.isVisible())
				{
					element.draw(graphics, input);
				}
			}
		}
	}

	public static void processTick(InputProvider input, TickInfo tickInfo, UiContainer... containers)
	{
		for (UiContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				if (element.isEnabled())
				{
					element.tick(input, tickInfo);
				}
			}
		}
	}

}
