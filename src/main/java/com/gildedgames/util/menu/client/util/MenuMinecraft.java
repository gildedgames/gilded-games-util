package com.gildedgames.util.menu.client.util;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import com.gildedgames.util.menu.client.ICustomSwitchButton;
import com.gildedgames.util.menu.client.IMenu;

public class MenuMinecraft implements IMenu
{

	@Override
	public String getID()
	{
		return "minecraft";
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
