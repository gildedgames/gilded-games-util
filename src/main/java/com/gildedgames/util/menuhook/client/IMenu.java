package com.gildedgames.util.menuhook.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
