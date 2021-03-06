package com.gildedgames.util.modules.tab.common;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.tab.api.TabAPI;
import com.gildedgames.util.modules.tab.client.inventory.TabBackpack;
import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabGroupHandler;
import com.gildedgames.util.modules.tab.common.util.TabGroupHandler;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * This is used to implement Tab functionality within various {@link GuiScreen} interfaces.
 * @author Brandon Pearce
 */
public class TabApiImpl implements TabAPI
{
	private final ITabGroupHandler inventoryTabGroup = new TabGroupHandler();

	private final Map<Integer, ITabGroupHandler> registeredGroups = new HashMap<>();

	private final ITab backpackTab = new TabBackpack();

	private ITabGroupHandler activeGroup;

	public TabApiImpl()
	{
		this.getInventoryGroup().registerServerTab(this.getBackpackTab());

		if (UtilModule.isClient())
		{
			this.getInventoryGroup().registerClientTab(new TabBackpack.Client());
		}

		this.registerGroup(this.inventoryTabGroup);
	}

	@Override
	public ITabGroupHandler getInventoryGroup()
	{
		return this.inventoryTabGroup;
	}

	@Override
	public ITab getBackpackTab()
	{
		return this.backpackTab;
	}

	@Override
	public Map<Integer, ITabGroupHandler> getRegisteredTabGroups()
	{
		return this.registeredGroups;
	}

	@Override
	public void registerGroup(ITabGroupHandler tabGroup)
	{
		this.getRegisteredTabGroups().put(this.getRegisteredTabGroups().size(), tabGroup);
	}

	public ITabGroupHandler getActiveGroup()
	{
		return this.activeGroup;
	}

	public void setActiveGroup(ITabGroupHandler activeGroup)
	{
		this.activeGroup = activeGroup;
	}
}
