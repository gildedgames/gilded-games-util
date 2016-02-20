package com.gildedgames.util.modules.tab.common.util;

import com.gildedgames.util.modules.tab.common.TabApiImpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This is the interface you should use to construct a Tab and its functionality. With two or more {@link ITab}s,
 * you can develop a whole {@link TabGroupHandler} to link to a {@link GuiScreen}'s interface.
 * @author Brandon Pearce
 */
public interface ITab
{

	/**
	 * This is the name which is displayed upon hovering over this {@link ITab} or when it is currently selected. Note that this is
	 * unlocalized and will be translated to the correct value if your mod uses a language file.
	 * @return This {@link ITab}'s unlocalized name which is used for rendering.
	 */
	@SideOnly(Side.CLIENT)
	String getUnlocalizedName();

	/**
	 * List all {@link GuiScreen} classes which are used within this tab. If you use a class which you have not listed here,
	 * the {@link TabApiImpl} will not be able to recognize its association with this particular tab, meaning it
	 * won't display when the {@link GuiScreen} is open.
	 * @return All of the {@link GuiScreen} classes which are used within this tab.
	 */
	@SideOnly(Side.CLIENT)
	boolean isTabValid(GuiScreen gui);

	/**
	 * This is where this {@link ITab}'s display icon is rendered
	 * @param x The X position of the {@link ITab}
	 * @param y The Y position of the {@link ITab}
	 */
	@SideOnly(Side.CLIENT)
	void renderIcon(int x, int y);

	/**
	 * Called when the player selects this {@link ITab} within its parent {@link TabGroupHandler}. This includes
	 * when the {@link TabGroupHandler} is opened up again and this {@link ITab} was its last-remembered tab.
	 * @param side The game's current networking Side.
	 */
	void onOpen(EntityPlayer player);

	/**
	 * Called when the player selects another {@link ITab} within this {@link ITab}'s parent {@link TabGroupHandler}. This includes
	 * when the {@link TabGroupHandler} is closed and this {@link ITab} was open.
	 * @param side The game's current networking Side.
	 */
	void onClose(EntityPlayer player);

	/**
	 * This is the current {@link Container} linked to this {@link ITab} upon opening it. Returns null if this {@link ITab} does
	 * not have an associated {@link Container}.
	 * @param side The game's current networking Side.
	 * @param player The player which opens this {@link ITab}
	 * @param world The world the player is currently in
	 * @param posX The X position of the player
	 * @param posY The Y position of the player
	 * @param posZ The Z position of the player
	 * @return The {@link Container} you'd like opened when this {@link ITab} is opened by the player.
	 * Return null if you don't want any {@link Container} opened.
	 */
	Container getCurrentContainer(EntityPlayer player, World world, int posX, int posY, int posZ);

	/**
	 * When an {@link ITab} is enabled, it will render as normal within its parent {@link TabGroupHandler}. However,
	 * if it is disabled, it will not render and can not be selected by the player.
	 * @param side The game's current networking Side.
	 * @return Whether or not this {@link ITab} is enabled
	 */
	boolean isEnabled();

	/**
	 * If an {@link ITab} can be remembered and it was the last {@link ITab} selected upon the closing of its parent
	 * {@link TabGroupHandler}, it will be opened up the next time that {@link TabGroupHandler} is reopened.
	 * @param side The game's current networking Side.
	 * @return Whether or not this {@link ITab} should be remembered by its parent {@link TabGroupHandler}
	 */
	boolean isRemembered();

}
