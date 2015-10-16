package com.gildedgames.util.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.RectHolder;
import com.gildedgames.util.ui.data.rect.RectListener;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiSorter;
import com.google.common.collect.ImmutableMap;

public class GuiCollection extends GuiFrame
{

	protected GuiPositioner positioner;

	protected GuiSorter sorter;

	protected List<ContentFactory> contentProviders = new ArrayList<ContentFactory>();

	private boolean isSorting;

	public GuiCollection(GuiPositioner positioner, ContentFactory... contentProviders)
	{
		this(Pos2D.flush(), 0, positioner, contentProviders);
	}

	public GuiCollection(Pos2D pos, int width, GuiPositioner positioner, ContentFactory... contentProviders)
	{
		super(Dim2D.build().pos(pos).width(width).flush());

		this.positioner = positioner;

		this.contentProviders.addAll(Arrays.<ContentFactory> asList(contentProviders));
	}

	public void setPositioner(GuiPositioner positioner)
	{
		this.positioner = positioner;
	}

	public GuiPositioner getPositioner()
	{
		return this.positioner;
	}

	public void setSorter(GuiSorter sorter)
	{
		this.sorter = sorter;
	}

	public GuiSorter getSorter()
	{
		return this.sorter;
	}

	private void clearAndProvideContent()
	{
		this.events().clear(Gui.class);

		for (ContentFactory contentProvider : this.contentProviders)
		{
			if (contentProvider != null)
			{
				LinkedHashMap<String, Ui> elements = contentProvider.provideContent(ImmutableMap.copyOf(this.events().map()), this.dim());
				for (Entry<String, Ui> entry : elements.entrySet())
				{
					RectHolder holder = ObjectFilter.cast(entry.getValue(), RectHolder.class);
					if (holder != null)
					{
						holder.dim().addListener(new RectListener()
						{
							@Override
							public void notifyDimChange(List<ModifierType> modifier)
							{
								if (!modifier.isEmpty() && !modifier.contains(ModifierType.X) && !modifier.contains(ModifierType.Y) && GuiCollection.this.isSorting)
								{
									GuiCollection.this.sortAndPositionContent();
								}
							}
						});
					}
				}
				this.events().setAll(elements);
			}
		}
	}

	private void positionContent(List<Gui> views)
	{
		this.positioner.positionList(views, this.dim());

		float totalContentHeight = 0;

		for (Gui gui : views)
		{
			if (gui != null)
			{
				//gui.dim().mod().addModifier(this, ModifierType.POS).flush();

				totalContentHeight = Math.max(totalContentHeight, gui.dim().y() + gui.dim().height());
			}
		}

		this.dim().mod().height(totalContentHeight).flush();
	}

	private void sortContent()
	{
		List<Gui> filteredViews = ObjectFilter.getTypesFrom(this.events().elements(), Gui.class);
		List<Gui> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;

		for (Gui view : filteredViews)
		{
			view.setVisible(false);
		}

		for (Gui view : sortedViews)
		{
			view.setVisible(true);
		}
	}

	private void sortAndPositionContent()
	{
		this.isSorting = true;
		this.positionContent(this.getSortedViews());
		this.sortContent();
		this.isSorting = false;
	}

	private List<Gui> getSortedViews()
	{
		List<Gui> filteredViews = ObjectFilter.getTypesFrom(this.events().elements(), Gui.class);
		List<Gui> sortedViews = this.sorter != null ? this.sorter.sortList(filteredViews) : filteredViews;

		return sortedViews;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.clearAndProvideContent();
		this.sortAndPositionContent();
		super.initContent(input);
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

}
