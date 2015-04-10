package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;

import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.ViewPositioner;
import com.gildedgames.util.ui.util.transform.ViewSorter;
import com.google.common.collect.ImmutableList;

public class UIButtonList extends UIBasicAbstract
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();
	
	protected ViewPositioner positioner;
	
	protected ViewSorter sorter;
	
	protected List<ContentFactory> contentProviders = new ArrayList<ContentFactory>();
	
	protected List<UIView> manualContent = new ArrayList<UIView>();

	public UIButtonList(Position2D pos, int width, ViewPositioner positioner, ContentFactory... contentProviders)
	{
		super(new Dimensions2D().setPos(pos).setWidth(width));
		
		this.positioner = positioner;
		
		this.contentProviders.addAll(Arrays.asList(contentProviders));
	}
	
	public void setPositioner(ViewPositioner positioner)
	{
		this.positioner = positioner;
	}
	
	public ViewPositioner getPositioner()
	{
		return this.positioner;
	}
	
	public void setSorter(ViewSorter sorter)
	{
		this.sorter = sorter;
	}
	
	public ViewSorter getSorter()
	{
		return this.sorter;
	}
	
	private void provideContent()
	{
		this.getListeners().clear(UIView.class);
		
		for (ContentFactory contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				super.getListeners().addAll(contentProvider.provideContent(ImmutableList.copyOf(this.getListeners().values())));
			}
		}
		
		this.getListeners().addAll(this.manualContent);
	}
	
	private void positionContent(List<UIView> views)
	{
		this.positioner.positionList(views, this.getDimensions());
		
		for (UIView view : FILTER.getTypesFrom(this.getListeners().values(), UIView.class))
		{
			view.getDimensions().setOrigin(this);
		}
	}
	
	private void sortContent()
	{
		List<UIView> filteredViews = FILTER.getTypesFrom(this.getListeners().values(), UIView.class);
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
		List<UIView> filteredViews = FILTER.getTypesFrom(this.getListeners().values(), UIView.class);
		List<UIView> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;
		
		return sortedViews;
	}
	
	public void refresh()
	{
		this.provideContent();
		this.sortAndPositionContent();
	}

	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);

		this.refresh();
		
		this.getDimensions().set(container.getCombinedDimensions());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.positionContent(this.getSortedViews());
		
		super.draw(graphics, input);
	}
	
}
