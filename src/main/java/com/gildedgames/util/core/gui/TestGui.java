package com.gildedgames.util.core.gui;

import java.awt.Color;

import net.minecraft.init.Blocks;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButtonItemStack;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.ButtonList;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.TestButtonFactory;
import com.gildedgames.util.ui.util.transform.GuiPositionerButton;

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

		//this.content().setElement("button", new MinecraftButtonItemStack(UtilCore.getItemStack(Blocks.anvil)));

		//this.content().getElement("button", GuiFrame.class).modDim().pos(new Pos2D(50, 50)).compile();

		this.content().setElement("text", GuiFactory.textBox(Dim2D.build().pos(100, 100).area(30, 90).compile(), true, GuiFactory.text("asdglakjawehglauhefajeliuvhaliuvhalieuhaliuhgliawugehliawughliawegdlgjawuefhaubh", Color.WHITE)));
	
		Dim2D dim = Dim2D.build().area(80, 200).compile();

		ButtonList buttonList = new ButtonList(new GuiPositionerButton(), new TestButtonFactory());

		ScrollableGui scrollable = new ScrollableGui(dim, buttonList, GuiFactory.createScrollBar());

		scrollable.modDim().pos(new Pos2D(50, 10)).compile();

		//this.content().setElement("scrollable", scrollable);
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
