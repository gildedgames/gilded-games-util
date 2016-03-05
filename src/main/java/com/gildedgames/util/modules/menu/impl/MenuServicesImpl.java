package com.gildedgames.util.modules.menu.impl;

import com.gildedgames.util.modules.menu.api.MenuServices;
import com.gildedgames.util.modules.menu.impl.client.IMenu;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.Collection;

public class MenuServicesImpl implements MenuServices
{
	private final ArrayList<IMenu> menus = new ArrayList<>();
	
	private IMenu current;

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
	
	public IMenu getPrevMenu()
	{
		int index = this.menus.indexOf(this.getCurrentMenu()) - 1;
		
		if (index < 0)
		{
			index = this.menus.size() - 1;
		}
		
		return this.menus.get(index);
	}

	@Override
	public IMenu getMenuFromScreen(GuiScreen screen)
	{
		if (screen != null)
		{
			for (IMenu menu : this.menus)
			{
				if (screen.getClass().isAssignableFrom(menu.getMenuClass()))
				{
					return menu;
				}
			}
		}

		return null;
	}

	@Override
	public Collection<IMenu> getRegisteredMenus()
	{
		return new ArrayList<>(this.menus);
	}

	@Override
	public IMenu getCurrentMenu()
	{
		return this.current;
	}

	@Override
	public void setCurrentMenu(IMenu menu)
	{
		this.current = menu;
	}

	@Override
	public void registerMenu(IMenu menu)
	{
		if (menu == null)
		{
			throw new IllegalArgumentException("Cannot register null as a menu!");
		}

		this.menus.add(menu);
	}
	
}
