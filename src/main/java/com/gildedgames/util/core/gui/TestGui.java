package com.gildedgames.util.core.gui;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.GuiCollection;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.TestButtonFactory2;
import com.gildedgames.util.ui.util.transform.GuiPositionerGrid;

public class TestGui extends GuiFrame
{

	private static final MinecraftAssetLocation FILE_BROWSER = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/file_browser/file.png");

	public TestGui()
	{
		super(Dim2D.flush());
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		//this.content().setElement("button", new MinecraftButtonItemStack(UtilCore.getItemStack(Blocks.anvil)));

		//this.content().getElement("button", GuiFrame.class).modDim().pos(new Pos2D(50, 50)).compile();

		//this.content().setElement("text", GuiFactory.textBox(Dim2D.build().pos(100, 100).area(30, 90).flush(), true, GuiFactory.text("asdglakjawehglauhefajeliuvhaliuvhalieuhaliuhgliawugehliawughliawegdlgjawuefhaubh", Color.WHITE)));

		Dim2D dim = Dim2D.build().area(70, 101).flush();

		GuiCollection buttonList = new GuiCollection(new Pos2D(), 60, new GuiPositionerGrid(), new TestButtonFactory2());

		ScrollableGui scrollable = new ScrollableGui(dim, buttonList, GuiFactory.createScrollBar());

		scrollable.modDim().pos(input.getScreenCenter()).center(true).flush();

		this.content().setElement("scrollable", scrollable);
		
		//this.content().setElement("fileB", new FileBrowserButton(Dim2D.build().pos(input.getScreenCenter()).center(true).scale(1.0F).flush(), GuiFactory.createTexture(new Sprite(FILE_BROWSER, UV.build().area(31, 31).flush())), "File.blueprint", new File("asdf"), "sdf"));
	
		//this.content().setElement("texture", GuiFactory.createResizablePanel(Dim2D.build().area(17, 200).flush()));
	
		//this.content().setElement("texture2", GuiFactory.createResizablePanel(Dim2D.build().area(300, 20).pos(40, 60).flush()));
		
		//this.content().setElement("texture3", GuiFactory.createResizablePanel(Dim2D.build().area(97, 116).center(true).x(input.getScreenCenter().getX()).y(input.getScreenHeight() - 100).flush()));
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
