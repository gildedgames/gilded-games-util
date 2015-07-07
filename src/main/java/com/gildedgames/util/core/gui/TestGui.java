package com.gildedgames.util.core.gui;

import java.io.File;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.core.gui.util.file_system.FileBrowserButton;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.input.InputProvider;

public class TestGui extends GuiFrame
{

	private static final MinecraftAssetLocation FILE_BROWSER = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/file_browser/file.png");

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

		/*this.content().setElement("text", GuiFactory.textBox(Dim2D.build().pos(100, 100).area(30, 90).compile(), true, GuiFactory.text("asdglakjawehglauhefajeliuvhaliuvhalieuhaliuhgliawugehliawughliawegdlgjawuefhaubh", Color.WHITE)));

		Dim2D dim = Dim2D.build().area(80, 200).compile();

		GuiCollection buttonList = new GuiCollection(new Pos2D(0, 0), 140, new GuiPositionerGrid(5, 50), new TestButtonFactory2());

		ScrollableGui scrollable = new ScrollableGui(dim, buttonList, GuiFactory.createScrollBar());

		scrollable.modDim().pos(new Pos2D(50, 10)).compile();

		this.content().setElement("scrollable", scrollable);*/

		this.content().setElement("fileB",
				new FileBrowserButton(Dim2D.build().pos(200, 50).compile(), GuiFactory.createTexture(new Sprite(FILE_BROWSER, 31, 31, 256, 256)), "xDYolo", new File("asdf"), "sdf"));

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
