package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyEventPool;
import com.gildedgames.util.ui.input.MouseEventPool;
import com.gildedgames.util.ui.listeners.IKeyboardListener;
import com.gildedgames.util.ui.listeners.IMouseListener;

public class UIElementWrapper implements UIBase
{

	protected final static ObjectFilter FILTER = new ObjectFilter();

	protected final List<UIElement> elements = new ArrayList<UIElement>();

	protected boolean visible = true, enabled = true, focused = false;

	/**
	 * @param containerDimensions Where the elements are drawn in
	 * @param screen The dimensions of the screen
	 * @param graphicsClass The class to use for the graphics
	 */
	public UIElementWrapper()
	{

	}

	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		for (UIView element : FILTER.getTypesFrom(this.elements, UIView.class))
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
	public boolean onKeyEvent(KeyEventPool pool)
	{
		for (IKeyboardListener element : FILTER.getTypesFrom(this.elements, IKeyboardListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled() && element.onKeyEvent(pool))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void onMouseEvent(InputProvider input, MouseEventPool pool)
	{
		for (IMouseListener element : FILTER.getTypesFrom(this.elements, IMouseListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled())
			{
				element.onMouseEvent(input, pool);
			}
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		for (IMouseListener element : FILTER.getTypesFrom(this.elements, IMouseListener.class))
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
	public void init(UIElementContainer elementcontainer, InputProvider input)
	{
		List<UIElement> filtered = null;
		List<UIElement> initialized = new ArrayList<UIElement>();
		
		while (!(filtered = FILTER.getTypesFrom(this.elements, UIElement.class)).equals(initialized))
		{
			for (UIElement element : filtered)
			{
				if (element == null || initialized.contains(element))
				{
					continue;
				}

				element.init(elementcontainer, input);
				initialized.add(element);
			}
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
	public Dimensions2D getDimensions()
	{
		List<Dimensions2D> areas = new ArrayList<Dimensions2D>();

		for (UIElement element : this.elements)
		{
			if (element instanceof UIView)
			{
				UIView view = (UIView) element;

				areas.add(view.getDimensions());
			}
		}

		return Dimensions2D.combine(areas);
	}

	@Override
	public void setDimensions(Dimensions2D dimensions)
	{

	}

	@Override
	public List<UIElement> getElements()
	{
		return this.elements;
	}

	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		List objectsToRemove = FILTER.getTypesFrom(this.elements, classToRemove);

		this.elements.removeAll(objectsToRemove);
	}

	@Override
	public boolean isFocused()
	{
		return this.focused;
	}

	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	@Override
	public List<UIView> queryAll(Object... input)
	{
		List<UIView> views = new ArrayList<UIView>();

		for (UIView element : FILTER.getTypesFrom(this.elements, UIView.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.query(input))
			{
				views.add(element);
			}
		}

		return views;
	}

	@Override
	public boolean query(Object... input)
	{
		for (UIView element : FILTER.getTypesFrom(this.elements, UIView.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.query(input))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean contains(UIElement element)
	{
		return this.elements.contains(element);
	}

}
