package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIOverhead;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Dimensions2DMutable;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.IKeyboardListener;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIElementWrapper implements UIOverhead
{

	protected final static ListFilter LIST_FILTER = new ListFilter();

	protected final List<UIElement> elements = new ArrayList<UIElement>();

	protected boolean visible = true;

	protected boolean enabled = true;

	protected Dimensions2D screen;

	/**
	 * @param holderDimensions Where the elements are drawn in
	 * @param screen The dimensions of the screen
	 * @param graphicsClass The class to use for the graphics
	 */
	public UIElementWrapper()
	{
		
	}

	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		for (UIView element : LIST_FILTER.getTypesFrom(this.elements, UIView.class))
		{
			if (element != null && element.isVisible())
			{
				element.draw(graphics, input);
			}
		}
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean onKeyState(char charTyped, int keyTyped, ButtonState state)
	{
		for (IKeyboardListener element : LIST_FILTER.getTypesFrom(this.elements, IKeyboardListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled() && element.onKeyState(charTyped, keyTyped, state))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void onMouseState(InputProvider input, MouseButton button, ButtonState state)
	{
		for (IMouseListener element : LIST_FILTER.getTypesFrom(this.elements, IMouseListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled())
			{
				element.onMouseState(input, button, state);
			}
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		for (IMouseListener element : LIST_FILTER.getTypesFrom(this.elements, IMouseListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled())
			{
				element.onMouseScroll(input, scrollDifference);
			}
		}
	}

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screen)
	{
		for (UIElement element : LIST_FILTER.getTypesFrom(this.elements, UIElement.class))
		{
			if (element == null)
			{
				continue;
			}

			element.init(elementHolder, screen);
		}
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public void add(UIElement element)
	{
		this.elements.add(element);
	}

	@Override
	public void remove(UIElement element)
	{
		this.elements.remove(element);
	}
	
	@Override
	public void clear()
	{
		this.elements.clear();
	}

	@Override
	public Dimensions2D getFocusArea()
	{
		List<Dimensions2D> areas = new ArrayList<Dimensions2D>();
		
		for (UIElement element : this.elements)
		{
			if (element instanceof UIView)
			{
				UIView view = (UIView)element;
				
				areas.add(new Dimensions2DMutable(view.getFocusArea()));
			}
		}
		
		return Dimensions2D.combine(areas);
	}

	@Override
	public void setFocusArea(Dimensions2D dimensions)
	{
		
	}

	@Override
	public void onFocused(InputProvider input)
	{
		for (UIView element : LIST_FILTER.getTypesFrom(this.elements, UIView.class))
		{
			if (element != null && element.isEnabled() && input.isHovered(element.getFocusArea()))
			{
				element.onFocused(input);
			}
		}
	}

	@Override
	public List<UIElement> getElements()
	{
		return this.elements;
	}

	@Override
	public void setScreen(Dimensions2D screen)
	{
		this.screen = screen;
	}

}
