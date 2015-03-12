package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIButtonList extends UIFrame
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();
	
	protected IViewPositioner positioner;
	
	protected IViewSorter sorter;
	
	protected List<IContentFiller> contentFillers = new ArrayList<IContentFiller>();
	
	public UIButtonList(Position2D pos, int width, IViewPositioner positioner, IViewSorter sorter, IContentFiller... contentFillers)
	{
		super(null, new Dimensions2D().setPos(pos).setWidth(width));
		
		this.positioner = positioner;
		this.sorter = sorter;
		
		this.contentFillers.addAll(Arrays.asList(contentFillers));
	}
	
	public void addContentFiller(IContentFiller contentFiller)
	{
		this.contentFillers.add(contentFiller);
	}
	
	public void refresh()
	{
		List<UIView> oldViews = FILTER.getTypesFrom(this.getElements(), UIView.class);
		
		super.clear(UIView.class);
		
		for (IContentFiller contentFiller : this.contentFillers)
		{
			if (contentFiller != null)
			{
				super.addAll(contentFiller.fillContent());
			}
		}
		
		List<UIElement> viewsToRemove = new ArrayList<UIElement>();
		
		for (UIElement element : this.getElements())
		{
			if (oldViews.contains(element))
			{
				viewsToRemove.add(element);
			}
		}
		
		oldViews.removeAll(viewsToRemove);
		
		super.addAll(oldViews);
		
		List<UIView> elements = this.sorter.sortList(FILTER.getTypesFrom(this.getElements(), UIView.class));
		this.positioner.positionList(elements, this.getDimensions());
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);
		
		this.refresh();
	}
	
	@Override
	public void add(UIElement element)
	{
		super.add(element);
		
		this.refresh();
	}
	
	@Override
	public void addAll(List<? extends UIElement> elements)
	{
		super.addAll(elements);
		
		this.refresh();
	}


	@Override
	public void remove(UIElement element)
	{
		super.remove(element);
		
		this.refresh();
	}
	
	@Override
	public void clear()
	{
		super.clear();
		
		this.refresh();
	}
	
	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		super.clear(classToRemove);
		
		this.refresh();
	}
	
}
