package com.gildedgames.util.ui.util.filebrowser;

import java.awt.Color;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.util.TextElement;

public class SimpleDropdownEntry implements DropdownEntry
{
	
	private String text;
	
	public SimpleDropdownEntry(String text)
	{
		this.text = text;
	}

	@Override
	public GuiFrame createVisuals()
	{
		return new TextElement(GuiFactory.text(this.text, Color.WHITE), Dim2D.flush());
	}

	@Override
	public void onOpen()
	{
		
	}

	@Override
	public void onHover(Pos2D cursorPos)
	{
		
	}

	@Override
	public DropdownMenu subMenu()
	{
		return null;
	}

}
