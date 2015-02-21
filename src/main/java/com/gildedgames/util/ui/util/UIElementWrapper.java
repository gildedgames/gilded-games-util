package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIGraphical;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.IKeyboardListener;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIElementWrapper implements UIFrame, UIElementHolder
{

	protected final static ListFilter LIST_FILTER = new ListFilter();

	protected final List<UIElement> elements = new ArrayList<UIElement>();

	protected boolean visible = true;

	protected boolean enabled = true;

	protected final Dimensions2D screenDimensions;

	protected Dimensions2D holderDimensions;

	/**
	 * @param holderDimensions Where the elements are drawn in
	 * @param screenDimensions The dimensions of the screen
	 * @param graphicsClass The class to use for the graphics
	 */
	public UIElementWrapper(Dimensions2D holderDimensions, Dimensions2D screenDimensions)
	{
		this.screenDimensions = screenDimensions;
		this.holderDimensions = holderDimensions;
	}

	@Override
	public void draw(IGraphics graphics)
	{
		for (UIGraphical element : LIST_FILTER.getTypesFrom(this.elements, UIGraphical.class))
		{
			if (element != null && element.isVisible())
			{
				element.draw(graphics);
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
	public void onMouseState(int mouseX, int mouseY, MouseButton button, ButtonState state)
	{
		for (IMouseListener element : LIST_FILTER.getTypesFrom(this.elements, IMouseListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled())
			{
				element.onMouseState(mouseX, mouseY, button, state);
			}
		}
	}

	@Override
	public void onMouseScroll(int mouseX, int mouseY, int scrollDifference)
	{
		for (IMouseListener element : LIST_FILTER.getTypesFrom(this.elements, IMouseListener.class))
		{
			if (element == null)
			{
				continue;
			}

			if (element.isEnabled())
			{
				element.onMouseScroll(mouseX, mouseY, scrollDifference);
			}
		}
	}

	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screenDimensions)
	{
		for (UIElement element : LIST_FILTER.getTypesFrom(this.elements, UIElement.class))
		{
			if (element == null)
			{
				continue;
			}

			element.init(this, screenDimensions);
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
		element.init(this, this.screenDimensions);

		this.elements.add(element);
	}

	@Override
	public void remove(UIElement element)
	{
		this.elements.remove(element);
	}

	@Override
	public Dimensions2D getDimensions()
	{
		return this.holderDimensions;
	}

	@Override
	public void setDimensions(Dimensions2D dimensions)
	{
		this.holderDimensions = dimensions;
	}

}
