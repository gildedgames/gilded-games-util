package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;

public final class GuiViewerHelper
{

	public static void processInitPre(Dim2DHolder parent, InputProvider input, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				if (element instanceof Dim2DHolder)
				{
					Dim2DHolder elementDimHolder = (Dim2DHolder) element;
					
					if (elementDimHolder.modDim() != null)
					{
						elementDimHolder.modDim().addModifier(parent, ModifierType.POS, ModifierType.SCALE).flush();
					}
				}

				element.init(input);

				Dim2DHolder parentModifier = ObjectFilter.getType(element, Dim2DHolder.class);

				if (parentModifier != null)
				{
					for (Ui child : element.seekContent())
					{
						Dim2DHolder dimHolder = ObjectFilter.getType(child, Dim2DHolder.class);

						if (dimHolder != null && dimHolder.modDim() != null)
						{
							dimHolder.modDim().addModifier(parentModifier, ModifierType.POS, ModifierType.SCALE).flush();
						}
					}
				}
			}
		}
	}

	public static void processResolutionChange(Dim2DHolder parent, InputProvider input, UIContainer... containers)
	{
		GuiViewerHelper.processInitPre(parent, input, containers);
	}

	public static void processClose(InputProvider input, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				if (element.isEnabled())
				{
					element.onClose(input);
				}
			}
		}
	}

	public static void processMouseInput(InputProvider input, MouseInputPool pool, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (MouseListener element : ObjectFilter.getTypesFrom(container.elements(), MouseListener.class))
			{
				if (element.isEnabled())
				{
					element.onMouseInput(pool, input);
				}
			}
		}
	}

	public static void processMouseScroll(InputProvider input, int scrollDifference, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (MouseListener element : ObjectFilter.getTypesFrom(container.elements(), MouseListener.class))
			{
				if (element.isEnabled())
				{
					element.onMouseScroll(scrollDifference, input);
				}
			}
		}
	}

	public static boolean processKeyboardInput(KeyboardInputPool pool, InputProvider input, UIContainer... containers)
	{
		boolean success = false;

		for (UIContainer container : containers)
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

					success = element.onKeyboardInput(pool, input) || success;
				}
			}
		}

		return success;
	}

	public static void processDraw(Graphics2D graphics, InputProvider input, UIContainer... containers)
	{
		for (UIContainer container : containers)
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

	public static void processTick(InputProvider input, TickInfo tickInfo, UIContainer... containers)
	{
		for (UIContainer container : containers)
		{
			if (container == null)
			{
				return;
			}

			for (Ui element : container.elements())
			{
				if (element.isEnabled())
				{
					element.tick(tickInfo, input);
				}
			}
		}
	}

}
