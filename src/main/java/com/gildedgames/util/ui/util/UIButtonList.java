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
import com.gildedgames.util.ui.input.InputProvider;
import com.google.common.collect.ImmutableList;

public class UIButtonList extends UIFrame
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();
	
	protected IViewPositioner positioner;
	
	protected IViewSorter sorter;
	
	protected List<IContentProvider> contentProviders = new ArrayList<IContentProvider>();
	
	protected List<UIView> manualContent = new ArrayList<UIView>();

	public UIButtonList(Position2D pos, int width, IViewPositioner positioner, IContentProvider... contentProviders)
	{
		super(null, new Dimensions2D().setPos(pos).setWidth(width));
		
		this.positioner = positioner;
		
		this.contentProviders.addAll(Arrays.asList(contentProviders));
	}
	
	public void setPositioner(IViewPositioner positioner)
	{
		this.positioner = positioner;
	}
	
	public IViewPositioner getPositioner()
	{
		return this.positioner;
	}
	
	public void setSorter(IViewSorter sorter)
	{
		this.sorter = sorter;
	}
	
	public IViewSorter getSorter()
	{
		return this.sorter;
	}
	
	private void provideContent()
	{
		super.clear(UIView.class);
		
		for (IContentProvider contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				super.addAll(contentProvider.provideContent(ImmutableList.copyOf(this.getElements())));
			}
		}
		
		this.addAll(this.manualContent);
	}
	
	public void sortAndPositionContent()
	{
		List<UIView> filteredViews = FILTER.getTypesFrom(this.getElements(), UIView.class);
		List<UIView> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;
		
		this.positioner.positionList(sortedViews, this.getDimensions());

		for (UIView view : filteredViews)
		{
			view.setVisible(false);
		}
		
		for (UIView view : sortedViews)
		{
			view.setVisible(true);
		}
	}
	
	public void refresh()
	{
		this.provideContent();
		this.sortAndPositionContent();
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
		
		if (element instanceof UIView)
		{
			this.manualContent.add((UIView) element);
		}
		
		this.sortAndPositionContent();
	}
	
	@Override
	public void addAll(Collection<? extends UIElement> elements)
	{
		super.addAll(elements);
		
		this.manualContent.addAll(FILTER.getTypesFrom(elements, UIView.class));
		
		this.sortAndPositionContent();
	}

	@Override
	public void remove(UIElement element)
	{
		super.remove(element);
		
		if (element instanceof UIView)
		{
			this.manualContent.remove((UIView) element);
		}
		
		this.sortAndPositionContent();
	}
	
	@Override
	public void clear()
	{
		super.clear();
		
		this.manualContent.clear();
		
		this.sortAndPositionContent();
	}
	
	@Override
	public void clear(Class<? extends UIElement> classToRemove)
	{
		super.clear(classToRemove);
		
		List objectsToRemove = FILTER.getTypesFrom(this.manualContent, classToRemove);

		this.manualContent.removeAll(objectsToRemove);
		
		this.sortAndPositionContent();
	}
	
}
