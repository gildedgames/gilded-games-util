package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Gui;
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

	public static void processInit(UiContainer container, InputProvider input)
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
						dimHolder.modDim().addModifier(parentModifier, ModifierType.POS).compile();
					}
				}
			}
			
			GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);
			
			if (frame != null)
			{
				GuiViewerHelper.processInit(frame.listeners(), input);
			}
			
			GuiViewerHelper.processInit(element.seekContent(), input);
		}
	}
	
	public static void processResolutionChange(UiContainer container, InputProvider input)
	{
		GuiViewerHelper.processInit(container, input);
	}
	
	public static void processMouseInput(UiContainer container, InputProvider input, MouseInputPool pool)
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
				
				GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);
				
				if (frame != null)
				{
					GuiViewerHelper.processMouseInput(frame.listeners(), input, pool);
				}
				
				GuiViewerHelper.processMouseInput(element.seekContent(), input, pool);
			}
		}
	}
	
	public static void processMouseScroll(UiContainer container, InputProvider input, int scrollDifference)
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
				
				GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);
				
				if (frame != null)
				{
					GuiViewerHelper.processMouseScroll(frame.listeners(), input, scrollDifference);
				}
				
				GuiViewerHelper.processMouseScroll(element.seekContent(), input, scrollDifference);
			}
		}
	}
	
	public static boolean processKeyboardInput(UiContainer container, KeyboardInputPool pool)
	{
		if (container == null)
		{
			return false;
		}
		
		boolean success = false;
		
		for (KeyboardListener element : ObjectFilter.getTypesFrom(container.elements(), KeyboardListener.class))
		{
			if (element.isEnabled())
			{
				GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);

				success = element.onKeyboardInput(pool)
						|| GuiViewerHelper.processKeyboardInput(element.seekContent(), pool)
						|| (frame != null && GuiViewerHelper.processKeyboardInput(frame.listeners(), pool))
						|| success;
			}
		}
		
		return success;
	}
	
	public static void processDraw(UiContainer container, Graphics2D graphics, InputProvider input)
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
				
				GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);
				
				if (frame != null)
				{
					GuiViewerHelper.processDraw(frame.listeners(), graphics, input);
				}
				
				GuiViewerHelper.processDraw(element.seekContent(), graphics, input);
			}
		}
	}
	
	public static void processTick(UiContainer container, InputProvider input, TickInfo tickInfo)
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

				GuiFrame frame = ObjectFilter.getType(element, GuiFrame.class);
				
				if (frame != null)
				{
					GuiViewerHelper.processTick(frame.listeners(), input, tickInfo);
				}
				
				GuiViewerHelper.processTick(element.seekContent(), input, tickInfo);
			}
		}
	}
	
}
