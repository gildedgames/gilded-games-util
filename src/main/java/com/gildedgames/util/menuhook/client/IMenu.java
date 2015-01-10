package com.gildedgames.util.menuhook.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IMenu
{

	GuiScreen getNewInstance();
	
	Class<? extends GuiScreen> getMenuClass();
	
	void onOpen();
	
	void onClose();

	boolean useCustomButtons();
	
	ICustomSwitchButton getLeftButton();
	
	ICustomSwitchButton getRightButton();
	
}
