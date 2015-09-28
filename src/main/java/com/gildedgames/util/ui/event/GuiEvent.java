package com.gildedgames.util.ui.event;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class GuiEvent extends UiEvent implements Gui
{
	
	private Gui gui;
	
	public GuiEvent(Gui gui)
	{
		this.gui = gui;
	}
	
	public Gui getGui()
	{
		return this.gui;
	}

	@Override
	public Dim2D getDim()
	{
		return null;
	}

	@Override
	public void setDim(Dim2D dim)
	{
		
	}

	@Override
	public Dim2DModifier modDim()
	{
		return null;
	}

	@Override
	public Dim2DBuilder copyDim()
	{
		return null;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		
	}

	@Override
	public boolean isVisible()
	{
		return true;
	}

	@Override
	public void setVisible(boolean visible)
	{
		
	}

	@Override
	public boolean isFocused()
	{
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		
	}

	@Override
	public boolean query(Object... input)
	{
		return false;
	}

	@Override
	public int ticksClosing()
	{
		return 0;
	}

	@Override
	public int ticksOpening()
	{
		return 0;
	}

}
