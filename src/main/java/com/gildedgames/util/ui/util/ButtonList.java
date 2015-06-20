package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.UIViewPositioner;
import com.gildedgames.util.ui.util.transform.UIViewSorter;
import com.google.common.collect.ImmutableMap;

public class ButtonList extends AbstractUI
{

	protected UIViewPositioner positioner;
	
	protected UIViewSorter sorter;
	
	protected List<ContentFactory> contentProviders = new ArrayList<ContentFactory>();
	
	public ButtonList(UIViewPositioner positioner, ContentFactory... contentProviders)
	{
		this(new Pos2D(), 0, positioner, contentProviders);
	}

	public ButtonList(Pos2D pos, int width, UIViewPositioner positioner, ContentFactory... contentProviders)
	{
		super(new Dim2D().setPos(pos).setWidth(width));
		
		this.positioner = positioner;
		
		this.contentProviders.addAll(Arrays.<ContentFactory>asList(contentProviders));
	}
	
	public void setPositioner(UIViewPositioner positioner)
	{
		this.positioner = positioner;
	}
	
	public UIViewPositioner getPositioner()
	{
		return this.positioner;
	}
	
	public void setSorter(UIViewSorter sorter)
	{
		this.sorter = sorter;
	}
	
	public UIViewSorter getSorter()
	{
		return this.sorter;
	}
	
	private void clearAndProvideContent()
	{
		this.getListeners().clear(UIView.class);
		
		for (ContentFactory contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				this.getListeners().setAllElements(contentProvider.provideContent(ImmutableMap.copyOf(this.getListeners().map()), this.getDim().immutable()));
			}
		}
	}
	
	private void positionContent(List<UIView> views)
	{
		this.positioner.positionList(views, this.getDim().immutable());
		
		int totalContentHeight = 0;
		
		for (UIView view : views)
		{
			if (view != null)
			{
				view.getDim().addModifier(this);
				
				totalContentHeight += view.getDim().getHeight();
			}
		}
		
		this.getDim().setHeight(totalContentHeight);
	}
	
	private void sortContent()
	{
		List<UIView> filteredViews = ObjectFilter.getTypesFrom(this.getListeners().values(), UIView.class);
		List<UIView> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;

		for (UIView view : filteredViews)
		{
			view.setVisible(false);
		}
		
		for (UIView view : sortedViews)
		{
			view.setVisible(true);
		}
	}
	
	private void sortAndPositionContent()
	{
		this.positionContent(this.getSortedViews());
		this.sortContent();
	}
	
	private List<UIView> getSortedViews()
	{
		List<UIView> filteredViews = ObjectFilter.getTypesFrom(this.getListeners().values(), UIView.class);
		List<UIView> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;
		
		return sortedViews;
	}
	
	public void refresh()
	{
		this.clearAndProvideContent();
		this.sortAndPositionContent();
	}

	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		super.onInit(container, input);

		this.refresh();

		//this.getDimensions().set(this.getListeners().getCombinedDimensions());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}
	
}
