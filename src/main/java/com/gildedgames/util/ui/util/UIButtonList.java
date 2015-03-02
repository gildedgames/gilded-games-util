package com.gildedgames.util.ui.util;

import java.util.List;

import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIButtonList extends UIFrame
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();
	
	protected IViewPositioner ordering;
	
	protected IViewSorter sorter;
	
	public UIButtonList(UIBase parent, IViewPositioner ordering, IViewSorter sorter)
	{
		super(parent, new Dimensions2D());
		
		this.ordering = ordering;
		this.sorter = sorter;
	}
	
	private void refreshList()
	{
		List<UIView> elements = this.sorter.sortList(FILTER.getTypesFrom(this.getElements(), UIView.class));
		this.ordering.positionList(elements, this.getDimensions());
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);
		
		this.refreshList();
	}
	
	@Override
	public void add(UIElement element)
	{
		super.add(element);
		
		this.refreshList();
	}

	@Override
	public void remove(UIElement element)
	{
		super.remove(element);
		
		this.refreshList();
	}
	
	@Override
	public void clear()
	{
		super.clear();
		
		this.refreshList();
	}
	
	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		super.clear(classToRemove);
		
		this.refreshList();
	}
	
}
