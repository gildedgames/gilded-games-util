package com.gildedgames.util.modules.tab.api;

import com.gildedgames.util.modules.tab.common.util.ITab;
import com.gildedgames.util.modules.tab.common.util.ITabGroupHandler;
import com.gildedgames.util.modules.tab.common.util.TabGroupHandler;
import net.minecraft.client.gui.GuiScreen;

import java.util.Map;

public interface TabAPI
{
	/**
	 * This method is used to register a {@link TabGroupHandler} to the {@link TabAPI}. A registered {@link TabGroupHandler} will be rendered
	 * onto the associated {@link GuiScreen}s and all functionality will be handled by the {@link TabAPI}. Note that if a
	 * {@link TabGroupHandler} is not registered, it will not function in-game.
	 * @param tabGroup The {@link TabGroupHandler} you'd like to register to the {@link TabAPI}
	 */
	void registerGroup(ITabGroupHandler tabGroup);

	ITabGroupHandler getActiveGroup();

	void setActiveGroup(ITabGroupHandler activeGroup);

	/**
	 * @return The default {@link TabGroupHandler} which holds a {@link #getBackpackTab() Backpack} {@link ITab} for the vanilla Inventory GUI.
	 */
	ITabGroupHandler getInventoryGroup();

	/**
	 * @return The default {@link ITab} associated with Minecraft's vanilla Inventory GUI.
	 */
	ITab getBackpackTab();


	/**
	 * @return A map of the registered {@link TabGroupHandler}s active within the game
	 */
	Map<Integer, ITabGroupHandler> getRegisteredTabGroups();
}
