package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scala.actors.threadpool.Arrays;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class UIButtonList extends UIFrame
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();
	
	protected IViewPositioner positioner;
	
	protected IViewSorter sorter;
	
	protected List<IContentProvider> contentProviders = new ArrayList<IContentProvider>();

	public UIButtonList(Position2D pos, int width, IViewPositioner positioner, IViewSorter sorter, IContentProvider... contentProviders)
	{
		super(null, new Dimensions2D().setPos(pos).setWidth(width));
		
		this.positioner = positioner;
		this.sorter = sorter;
		
		this.contentProviders.addAll(Arrays.asList(contentProviders));
	}
	
	public void refresh()
	{
		super.clear(UIView.class);
		
		for (IContentProvider contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				super.addAll(contentProvider.provideContent());
			}
		}

		List<UIView> filteredViews = FILTER.getTypesFrom(this.getElements(), UIView.class);
		List<UIView> sortedViews = this.sorter.sortList(filteredViews);
		
		this.positioner.positionList(sortedViews, this.getDimensions());

		for (UIView view : filteredViews)
		{
			view.setVisible(false);
		}
		
		for (UIView view : sortedViews)
		{
			System.out.println("Hee");
			view.setVisible(true);
		}
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
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
	public void addAll(Collection<? extends UIElement> elements)
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
