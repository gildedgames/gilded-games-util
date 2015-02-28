package com.gildedgames.util.ui;

import com.gildedgames.util.core.gui.UIButtonFactoryMC;
import com.gildedgames.util.core.gui.UIFrameMC;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class TestUI extends UIFrameMC
{

	public TestUI()
	{
		super(new Dimensions2D().set(50, 50));
	}
	
	@Override
	public void init(UIElementHolder holder, Dimensions2D screen)
	{
		super.init(holder, screen);

		Position2D center = new Position2D(screen.getWidth() / 2, screen.getHeight() / 2);
		
		/*DrawingData data = new DrawingData(new Color(1.0F, 0.0F, 0.0F, 1F));
		
		UIRectangle rect = new UIRectangle(new Dimensions2D().set(center).set(50, 50).set(true).set(2.0F), data);
		
		Resource hopperTexture = new Resource(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
		Sprite hopperSprite = new Sprite(hopperTexture, 0, 0, 256, 153, 256, 256);
		
		Dimensions2D dim = new Dimensions2D().set(center).set(256, 153).set(true);

		holder.add(new UITexture(hopperSprite, dim.set(1.1F), new DrawingData(new Color(0.5F, 0.0F, 1.0F, 1.0F))));
		
		UITexture hopperResized = new UITexture(hopperSprite, dim.set(new Position2D(60, 60)).set(1.2F));

		hopperResized.setDrawingData(new DrawingData(new Color(0.0F, 0.0F, 1.0F, 0.4F)));*/

		UIButtonFactoryMC buttonFactory = new UIButtonFactoryMC();

		UIOverhead button = buttonFactory.createArrowButton(center);
		UIOverhead button2 = buttonFactory.createArrowButton(center.add(-50, -50));
		UIOverhead button3 = buttonFactory.createArrowButton(center);
		
		holder.add(button);
		holder.add(button2);
		holder.add(button3);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}

	@Override
	public void onFocused(InputProvider input)
	{
		
	}

}
