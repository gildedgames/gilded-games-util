package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.UIViewPositioner;
import com.gildedgames.util.ui.util.transform.UIViewSorter;
import com.google.common.collect.ImmutableMap;

public class ButtonList extends UIFrame
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
		super(Dim2D.build().pos(pos).width(width).commit());
		
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
		this.listeners().clear(UIView.class);
		
		for (ContentFactory contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				this.listeners().setAllElements(contentProvider.provideContent(ImmutableMap.copyOf(this.listeners().map()), this.getDim()));
			}
		}
	}
	
	private void positionContent(List<UIView> views)
	{
		this.positioner.positionList(views, this.getDim());
		
		int totalContentHeight = 0;
		
		for (UIView view : views)
		{
			if (view != null)
			{
				view.modDim().addModifier(this).commit();
				
				totalContentHeight += view.getDim().getHeight();
			}
		}
		
		this.modDim().height(totalContentHeight).commit();
	}
	
	private void sortContent()
	{
		List<UIView> filteredViews = ObjectFilter.getTypesFrom(this.listeners().elements(), UIView.class);
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
		List<UIView> filteredViews = ObjectFilter.getTypesFrom(this.listeners().elements(), UIView.class);
		List<UIView> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;
		
		return sortedViews;
	}
	
	public void refresh()
	{
		this.clearAndProvideContent();
		this.sortAndPositionContent();
	}

	@Override
	public void init(InputProvider input)
	{
		super.init(input);

		this.refresh();

		//this.getDimensions().set(this.getListeners().getCombinedDimensions());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}
	
}
