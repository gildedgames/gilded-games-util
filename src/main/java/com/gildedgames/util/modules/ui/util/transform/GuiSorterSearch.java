package com.gildedgames.util.modules.ui.util.transform;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.modules.ui.common.Gui;

public class GuiSorterSearch implements GuiSorter
{
	
	protected String searchText = "";
	
	public GuiSorterSearch()
	{
		
	}
	
	public void setSearchText(String searchText)
	{
		this.searchText = searchText;
	}

	@Override
	public List<Gui> sortList(List<Gui> list)
	{
		if (this.searchText == null || this.searchText.isEmpty())
		{
			return list;
		}
		
		List<Gui> result = new ArrayList<>();

		for (Gui view : list)
		{
			if (view != null && view.query(this.searchText))
			{
				result.add(view);
			}
		}
		
		return result;
	}

}
