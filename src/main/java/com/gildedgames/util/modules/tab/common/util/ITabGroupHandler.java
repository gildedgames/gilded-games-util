package com.gildedgames.util.modules.tab.common.util;

public interface ITabGroupHandler
{
	void registerServerTab(ITab tab);

	void registerClientTab(ITabClient tab);

	int getDiscriminant(ITab tab);

	ITabGroup<ITabClient> getClientGroup();

	ITabGroup<ITab> getServerGroup();
}
