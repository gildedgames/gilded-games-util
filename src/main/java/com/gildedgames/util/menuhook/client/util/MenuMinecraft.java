package com.gildedgames.util.menuhook.client.util;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import com.gildedgames.util.menuhook.client.ICustomSwitchButton;
import com.gildedgames.util.menuhook.client.IMenu;

public class MenuMinecraft implements IMenu
{

	@Override
	public String getUnlocalizedName()
	{
		return "menu.minecraft.name";
	}
	
	@Override
	public String getUnlocalizedDescription()
	{
		return "menu.minecraft.description";
	}

	@Override
	public GuiScreen getNewInstance()
	{
		return new GuiMainMenu();
	}

	@Override
	public void onOpen()
	{
		
	}

	@Override
	public void onClose()
	{
		
	}

	@Override
	public Class<? extends GuiScreen> getMenuClass()
	{
		return GuiMainMenu.class;
	}

	@Override
	public boolean useCustomButtons()
	{
		return false;
	}

	@Override
	public ICustomSwitchButton getLeftButton()
	{
		return null;
	}

	@Override
	public ICustomSwitchButton getRightButton()
	{
		return null;
	}

}
