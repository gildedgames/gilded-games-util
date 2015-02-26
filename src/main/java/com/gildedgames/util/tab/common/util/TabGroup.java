package com.gildedgames.util.tab.common.util;

import java.util.ArrayList;
import java.util.List;

public class TabGroup implements ITabGroup
{
	
	protected final ArrayList<ITab> tabs = new ArrayList<ITab>();
	
	protected ITab selectedTab, rememberedTab;

	protected boolean rememberSelectedTab = true;
	
	public TabGroup()
	{
		
	}
	
	@Override
	public boolean getRememberSelectedTab()
	{
		return this.rememberSelectedTab;
	}

	@Override
	public void setRememberSelectedTab(boolean rememberSelectedTab)
	{
		this.rememberSelectedTab = rememberSelectedTab;
	}

	@Override
	public ITab getSelectedTab()
	{
		return this.selectedTab;
	}
	
	@Override
	public void setSelectedTab(ITab tab)
	{
		this.selectedTab = tab;
	}

	@Override
	public ITab getRememberedTab()
	{
		return this.rememberedTab;
	}

	@Override
	public void setRememberedTab(ITab tab)
	{
		this.rememberedTab = tab;
	}

	@Override
	public void add(ITab tab)
	{
		this.tabs.add(tab);
	}

	@Override
	public int getTabAmount()
	{
		int amount = 0;

		for (ITab tabDescription : this.tabs)
		{
			if (tabDescription.isEnabled())
			{
				amount++;
			}
		}

		return amount;
	}

	@Override
	public List<ITab> getEnabledTabs()
	{
		List<ITab> enabledTabs = new ArrayList<ITab>();

		for (ITab tabDescription : this.tabs)
		{
			if (tabDescription.isEnabled())
			{
				enabledTabs.add(tabDescription);
			}
		}

		return enabledTabs;
	}
	
	@Override
	public List<ITab> getTabs()
	{
		return this.tabs;
	}
	
}
