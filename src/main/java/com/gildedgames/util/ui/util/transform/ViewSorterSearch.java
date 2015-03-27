package com.gildedgames.util.ui.util.transform;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.ui.UIView;

public class ViewSorterSearch implements ViewSorter
{
	
	protected String searchText = "";
	
	public ViewSorterSearch()
	{
		
	}
	
	public void setSearchText(String searchText)
	{
		this.searchText = searchText;
	}

	@Override
	public List<UIView> sortList(List<UIView> list)
	{
		if (this.searchText == null || this.searchText.isEmpty())
		{
			return list;
		}
		
		List<UIView> result = new ArrayList<UIView>();

		for (UIView view : list)
		{
			if (view != null && view.query(this.searchText))
			{
				result.add(view);
			}
		}
		
		return result;
	}

}
