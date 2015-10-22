package com.gildedgames.util.group.client;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.input.GuiInput;
import com.gildedgames.util.ui.util.input.StringInput;

public class GuiCreateGroup extends GuiFrame
{
	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
		this.content().set("cake?", new GuiInput<String>(new StringInput(), Dim2D.build().pos(100, 100).area(100, 30).flush(), "Insert party name"));

	}
}
