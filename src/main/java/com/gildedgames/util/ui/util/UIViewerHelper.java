package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIView;
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

public final class UIViewerHelper
{

	public static void processInit(UIContainer container, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (UIElement element : container.elements())
		{
			Dim2DHolder parentModifier = ObjectFilter.getType(element, Dim2DHolder.class);

			element.init(input);

			if (parentModifier != null)
			{
				for (UIElement child : element.seekContent())
				{
					Dim2DHolder dimHolder = ObjectFilter.getType(child, Dim2DHolder.class);
					
					if (dimHolder != null)
					{
						dimHolder.modDim().addModifier(parentModifier, ModifierType.POS).compile();
					}
				}
			}
			
			UIFrame frame = ObjectFilter.getType(element, UIFrame.class);
			
			if (frame != null)
			{
				UIViewerHelper.processInit(frame.listeners(), input);
			}
			
			UIViewerHelper.processInit(element.seekContent(), input);
		}
	}
	
	public static void processResolutionChange(UIContainer container, InputProvider input)
	{
		UIViewerHelper.processInit(container, input);
	}
	
	public static void processMouseInput(UIContainer container, InputProvider input, MouseInputPool pool)
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
				
				UIFrame frame = ObjectFilter.getType(element, UIFrame.class);
				
				if (frame != null)
				{
					UIViewerHelper.processMouseInput(frame.listeners(), input, pool);
				}
				
				UIViewerHelper.processMouseInput(element.seekContent(), input, pool);
			}
		}
	}
	
	public static void processMouseScroll(UIContainer container, InputProvider input, int scrollDifference)
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
				
				UIFrame frame = ObjectFilter.getType(element, UIFrame.class);
				
				if (frame != null)
				{
					UIViewerHelper.processMouseScroll(frame.listeners(), input, scrollDifference);
				}
				
				UIViewerHelper.processMouseScroll(element.seekContent(), input, scrollDifference);
			}
		}
	}
	
	public static boolean processKeyboardInput(UIContainer container, KeyboardInputPool pool)
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
				UIFrame frame = ObjectFilter.getType(element, UIFrame.class);

				success = element.onKeyboardInput(pool)
						|| UIViewerHelper.processKeyboardInput(element.seekContent(), pool)
						|| (frame != null && UIViewerHelper.processKeyboardInput(frame.listeners(), pool))
						|| success;
			}
		}
		
		return success;
	}
	
	public static void processDraw(UIContainer container, Graphics2D graphics, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (UIView element : ObjectFilter.getTypesFrom(container.elements(), UIView.class))
		{
			if (element.isVisible())
			{
				element.draw(graphics, input);
				
				UIFrame frame = ObjectFilter.getType(element, UIFrame.class);
				
				if (frame != null)
				{
					UIViewerHelper.processDraw(frame.listeners(), graphics, input);
				}
				
				UIViewerHelper.processDraw(element.seekContent(), graphics, input);
			}
		}
	}
	
	public static void processTick(UIContainer container, InputProvider input, TickInfo tickInfo)
	{
		if (container == null)
		{
			return;
		}
		
		for (UIElement element : container.elements())
		{
			if (element.isEnabled())
			{
				element.tick(input, tickInfo);

				UIFrame frame = ObjectFilter.getType(element, UIFrame.class);
				
				if (frame != null)
				{
					UIViewerHelper.processTick(frame.listeners(), input, tickInfo);
				}
				
				UIViewerHelper.processTick(element.seekContent(), input, tickInfo);
			}
		}
	}
	
}
