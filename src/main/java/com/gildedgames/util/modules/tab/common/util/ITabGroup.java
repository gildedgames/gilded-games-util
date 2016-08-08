package com.gildedgames.util.modules.tab.common.util;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public interface ITabGroup<T extends ITab>
{

	/**
	 * This adds an {@link ITab} to this {@link TabGroupHandler}, allowing it to render. If this {@link TabGroupHandler} only
	 * contains one {@link ITab}, it will not render over the {@link GuiScreen} interface.
	 * @param tab The {@link ITab} you'd like added to this {@link TabGroupHandler}
	 */
	void add(T tab);

	/**
	 * @return The amount of {@link ITab}s contained within this {@link TabGroupHandler}
	 */
	int getTabAmount();

	/**
	 * @return A List of the {@link TabGroupHandler}'s contained {@link ITab}s which are currently enabled. This is achieved through checking their {@link ITab#isEnabled(Side)} method
	 */
	List<T> getEnabledTabs();

	/**
	 * @return A List of the {@link TabGroupHandler}'s contained {@link ITab}s, regardless of if they are enabled or not.
	 * Just note that this List includes disabled {@link ITab}s as well.
	 */
	List<T> getTabs();

	/**
	 * If this {@link TabGroupHandler} remembers the selected {@link ITab}, it'll open up that {@link ITab} next time
	 * the {@link TabGroupHandler} is re-opened.
	 * @return Whether this {@link TabGroupHandler} should remember its last selected tab or not
	 */
	boolean getRememberSelectedTab();

	/**
	 * If this {@link TabGroupHandler} remembers the selected {@link ITab}, it'll open up that {@link ITab} next time
	 * the {@link TabGroupHandler} is re-opened.
	 * @param rememberSelectedTab Whether this {@link TabGroupHandler} should remember its last selected tab or not
	 */
	void setRememberSelectedTab(boolean rememberSelectedTab);

	/**
	 * The selected {@link ITab} in a {@link TabGroupHandler} will display its associated {@link GuiScreen}
	 * and {@link Container} to the player.
	 * @return The selected {@link ITab} in this {@link TabGroupHandler}
	 */
	T getSelectedTab();

	/**
	 * The selected {@link ITab} in a {@link TabGroupHandler} will display its associated {@link GuiScreen}
	 * and {@link Container} to the player.
	 * @param tab The {@link ITab} you'd like selected within this {@link TabGroupHandler}
	 */
	void setSelectedTab(T tab);

	/**
	 * The remembered {@link ITab} in a {@link TabGroupHandler} will open up the next time its parent
	 * {@link TabGroupHandler} is opened.
	 * @return The currently-remembered {@link ITab}
	 */
	T getRememberedTab();

	/**
	 * This makes the passed {@link ITab} remembered by this {@link TabGroupHandler}, meaning the next time
	 * this {@link TabGroupHandler} is opened, it will open to the currently-remembered {@link ITab}.
	 * @param tab The {@link ITab} you'd like this {@link TabGroupHandler} to remember
	 */
	void setRememberedTab(T tab);

}
