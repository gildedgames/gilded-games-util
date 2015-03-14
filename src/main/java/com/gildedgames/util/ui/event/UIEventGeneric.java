package com.gildedgames.util.ui.event;

import java.util.Collection;
import java.util.Collections;
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

public class UIEventGeneric implements UIBase
{

	private final Dimensions2D dim = new Dimensions2D();
	
	private boolean enabled = true, visible = true;

	@Override
	public void init(UIElementContainer elementcontainer, InputProvider input)
	{
		
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
	public void draw(IGraphics graphics, InputProvider input)
	{
		
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
	public Dimensions2D getDimensions()
	{
		return this.dim;
	}

	@Override
	public void setDimensions(Dimensions2D dim)
	{
		
	}

	@Override
	public boolean isFocused()
	{
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		
	}

	@Override
	public boolean query(List input)
	{
		return false;
	}

	@Override
	public void add(UIElement element)
	{
		
	}

	@Override
	public void addAll(Collection<? extends UIElement> elements)
	{
		
	}

	@Override
	public void remove(UIElement element)
	{
		
	}

	@Override
	public void removeAll(Collection<? extends UIElement> elements)
	{
		
	}

	@Override
	public void clear()
	{
		
	}

	@Override
	public boolean contains(UIElement element)
	{
		return false;
	}

	@Override
	public int size()
	{
		return 0;
	}

	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		
	}

	@Override
	public List<UIElement> getElements()
	{
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<UIView> queryAll(List input)
	{
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean onKeyEvent(KeyEventPool pool)
	{
		return false;
	}

	@Override
	public void onMouseEvent(InputProvider input, MouseEventPool pool)
	{
		
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		
	}

}
