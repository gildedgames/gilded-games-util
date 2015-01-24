package com.gildedgames.util.menu.client;

import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMenu
{
	
	/**
	 * The ID that represents this IMenu. Used to save and retrieve which menu the player last used.
	 * "minecraft" is reserved for the default Minecraft main menu.
	 * @return
	 */
	String getID();

	GuiScreen getNewInstance();
	
	Class<? extends GuiScreen> getMenuClass();
	
	void onOpen();
	
	void onClose();

	boolean useCustomButtons();
	
	ICustomSwitchButton getLeftButton();
	
	ICustomSwitchButton getRightButton();
	
}
