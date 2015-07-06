package com.gildedgames.util.core.gui;

import net.minecraft.init.Blocks;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButtonItemStack;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class TestGui extends GuiFrame
{

	public TestGui()
	{
		super(Dim2D.compile());
	}

	@Override
	public void init(InputProvider input)
	{
		super.init(input);
		
		this.content().setElement("button", new MinecraftButtonItemStack(UtilCore.getItemStack(Blocks.anvil)));
		
		this.content().getElement("button", GuiFrame.class).modDim().pos(new Pos2D(50, 50)).compile();
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		super.tick(input, tickInfo);
	}

}
