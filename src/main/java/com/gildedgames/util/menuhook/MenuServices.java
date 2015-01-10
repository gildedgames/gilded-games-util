package com.gildedgames.util.menuhook;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.menuhook.client.IMenu;

public class MenuServices
{

	protected List<IMenu> menus = new ArrayList<IMenu>();
	
	protected IMenu current;	
	
	public MenuServices()
	{
		
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
