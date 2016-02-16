package com.gildedgames.util.tab.common;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.tab.api.TabAPI;
import com.gildedgames.util.tab.client.inventory.TabBackpack;
import com.gildedgames.util.tab.common.util.ITab;
import com.gildedgames.util.tab.common.util.ITabGroupHandler;
import com.gildedgames.util.tab.common.util.TabGroupHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@SideOnly(Side.CLIENT)
	private final ITab BACKPACK_TAB = new TabBackpack();

	private ITabGroupHandler activeGroup;

	public TabApiImpl()
	{
		if (UtilModule.isClient())
		{
			this.getInventoryGroup().getSide(Side.CLIENT).add(this.getBackpackTab());

			this.registerGroup(this.inventoryTabGroup);
		}
	}

	@Override
	public ITabGroupHandler getInventoryGroup()
	{
		return this.inventoryTabGroup;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ITab getBackpackTab()
	{
		return this.BACKPACK_TAB;
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
