package com.gildedgames.util.core.gui;

import java.awt.Color;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;

public class PaynGui extends GuiFrame
{

	public PaynGui()
	{
		super(Dim2D.compile());
	}

	@Override
	public void init(InputProvider input)
	{
		Dim2D rectangleDim = Dim2D.build().y(20).x(50).width(50).height(2000).compile();

		RectangleElement rectangle = new RectangleElement(rectangleDim, new DrawingData(new Color(403959)), new DrawingData(new Color(0xA30000)));

		GuiFrame scrollableRectangle = new ScrollableGui(rectangleDim.clone().height(200).width(70).compile(), rectangle, GuiFactory.createScrollBar());

		//this.content().setElement("rectangle", scrollableRectangle);

		Button batmanButton = GuiFactory.createBatmanButton(Dim2D.build().x(50).y(50).width(100).height(60).compile());

		this.content().setElement("batmanButton", batmanButton);
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		ScrollableGui rectangle = this.content().getElement("rectangle", ScrollableGui.class);

		if (pool.has(MouseButton.LEFT) && input.isHovered(rectangle))
		{
			/*Random random = new Random();

			int randomX = random.nextInt(input.getScreenWidth());
			int randomY = random.nextInt(input.getScreenHeight());

			MinecraftAssetLocation testAsset = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/someFolder/someTexture.png");

			UIFrame texture = UIFactory.createTexture(testAsset);

			texture.modDim().x(randomX).y(randomY).scale(random.nextFloat()).compile();

			this.content().setElement("texture" + random.nextInt(50000), texture);*/
		}
	}
}
