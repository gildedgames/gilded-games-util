package com.gildedgames.util.modules.menu.api;

import com.gildedgames.util.modules.menu.impl.client.IMenu;
import net.minecraft.client.gui.GuiScreen;

import java.util.Collection;

public interface MenuServices
{
	/**
	 * Returns the current menu.
	 */
	IMenu getCurrentMenu();

	/**
	 * Sets the current menu.
	 * @param menu The menu to change to
	 */
	void setCurrentMenu(IMenu menu);

	/**
	 * Registers a menu.
	 * @param menu The menu to register
	 */
	void registerMenu(IMenu menu);

	/**
	 * Returns a list of currently registered menus.
	 */
	Collection<IMenu> getRegisteredMenus();

	/**
	 * Gets a menu by it's identifier.
	 * @param id The identifier
	 * @return The menu
	 */
	IMenu getMenuFromID(String id);

	/**
	 * Finds the menu implementation of the GUIScreen.
	 * @param screen The screen
	 * @return The menu implementation
	 */
	IMenu getMenuFromScreen(GuiScreen screen);

	/**
	 * Returns the next available menu.
	 */
	IMenu getNextMenu();

	/**
	 * Returns the next previous available menu.
	 */
	IMenu getPrevMenu();
}
