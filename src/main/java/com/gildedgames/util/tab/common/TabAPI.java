package com.gildedgames.util.tab.common;

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
public enum TabAPI
{
	
	INSTANCE;

	private final Map<Integer, ITabGroupHandler> registeredGroups = new HashMap<Integer, ITabGroupHandler>();

	private ITabGroupHandler activeGroup;
	
	private final static ITabGroupHandler INVENTORY_TAB_GROUP = new TabGroupHandler();
	
	@SideOnly(Side.CLIENT)
	private static ITab BACKPACK_TAB;
	
	/**
	 * @return The default {@link TabGroupHandler} which holds a {@link #getBackpackTab() Backpack} {@link ITab} for the vanilla Inventory GUI.
	 */
	public static ITabGroupHandler getInventoryGroup()
	{
		return TabAPI.INVENTORY_TAB_GROUP;
	}
	
	/**
	 * @return The default {@link ITab} associated with Minecraft's vanilla Inventory GUI.
	 */
	@SideOnly(Side.CLIENT)
	public static ITab getBackpackTab()
	{
		return TabAPI.BACKPACK_TAB;
	}
	
	public static void setBackpackTab(ITab tab)
	{
		TabAPI.BACKPACK_TAB = tab;
	}
	
	/**
	 * @return A map of the registered {@link TabGroupHandler}s active within the game
	 */
	public Map<Integer, ITabGroupHandler> getRegisteredTabGroups()
	{
		return registeredGroups;
	}

	/**
	 * This method is used to register a {@link TabGroupHandler} to the {@link TabAPI}. A registered {@link TabGroupHandler} will be rendered
	 * onto the associated {@link GuiScreen}s and all functionality will be handled by the {@link TabAPI}. Note that if a
	 * {@link TabGroupHandler} is not registered, it will not function in-game.
	 * @param tabGroup The {@link TabGroupHandler} you'd like to register to the {@link TabAPI}
	 */
	public void register(ITabGroupHandler tabGroup)
	{
		this.getRegisteredTabGroups().put(this.getRegisteredTabGroups().size(), tabGroup);
	}

	public ITabGroupHandler getActiveGroup()
	{
		return activeGroup;
	}

	public void setActiveGroup(ITabGroupHandler activeGroup)
	{
		this.activeGroup = activeGroup;
	}
	
}
