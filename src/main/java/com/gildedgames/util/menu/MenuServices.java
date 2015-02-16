package com.gildedgames.util.menu;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

import com.gildedgames.util.menu.client.IMenu;

public class MenuServices
{

	protected List<IMenu> menus = new ArrayList<IMenu>();
	
	protected IMenu current;	
	
	public MenuServices()
	{
		
	}
	
	public IMenu getMenuFromID(String id)
	{
		for (IMenu menu : this.menus)
		{
			if (menu != null && menu.getID().equals(id))
			{
				return menu;
			}
		}
		
		return null;
	}
	
	public IMenu getNextMenu()
	{
		int index = this.menus.indexOf(this.getCurrentMenu()) + 1;
		
		if (index >= this.menus.size())
		{
			index = 0;
		}
		
		return this.menus.get(index);
	}
	
	public IMenu getPreviousMenu()
	{
		int index = this.menus.indexOf(this.getCurrentMenu()) - 1;
		
		if (index < 0)
		{
			index = this.menus.size() - 1;
		}
		
		return this.menus.get(index);
	}
	
	public IMenu fromGui(GuiScreen screen)
	{
		for (IMenu menu : this.menus)
		{
			if (screen != null && menu != null && screen.getClass().isAssignableFrom(menu.getMenuClass()))
			{
				return menu;
			}
		}
		return null;
	}
	
	public List<IMenu> getRegisteredMenus()
	{
		return new ArrayList<IMenu>(this.menus);
	}
	
	public void setCurrentMenu(IMenu menu)
	{
		this.current = menu;
	}
	
	public IMenu getCurrentMenu()
	{
		return this.current;
	}
	
	public void registerMenu(IMenu menu)
	{
		this.menus.add(menu);
	}
	
}
