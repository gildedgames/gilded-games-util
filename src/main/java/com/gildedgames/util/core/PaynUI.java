package com.gildedgames.util.core;

import java.awt.Color;
import java.util.Random;

import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.core.gui.util.UIFactory;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.decorators.ScrollableUI;

public class PaynUI extends UIFrame
{

	public PaynUI()
	{
		super(Dim2D.compile());
	}
	
	@Override
	public void init(InputProvider input)
	{
		Dim2D rectangleDim = Dim2D.build().y(20).x(50).width(50).height(2000).compile();

		RectangleElement rectangle = new RectangleElement(rectangleDim, new DrawingData(new Color(403959)), new DrawingData(new Color(0xA30000)));
		
		UIFrame scrollableRectangle = new ScrollableUI(rectangleDim.clone().height(200).width(70).compile(), rectangle, UIFactory.createScrollBar());
		
		this.content().setElement("rectangle", scrollableRectangle);
		
		
	}
	
	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		ScrollableUI rectangle = this.content().getElement("rectangle", ScrollableUI.class);

		if (pool.has(MouseButton.LEFT) && input.isHovered(rectangle))
		{
            Random random = new Random();
            
            int randomX = random.nextInt(input.getScreenWidth());
            int randomY = random.nextInt(input.getScreenHeight());
			
			MinecraftAssetLocation testAsset = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/someFolder/someTexture.png");
			 
			int assetWidth = 310;
			int assetHeight = 310;
	 
			int u = 0;
			int v = 0;
	 
			int textureWidth = 310;
			int textureHeight = 310;
	 
			Sprite testSprite = new Sprite(testAsset, u, v, textureWidth, textureHeight, assetWidth, assetHeight);
	 
			UIFrame texture = UIFactory.createTexture(testSprite, Dim2D.build().x(randomX).y(randomY).compile());
			
			this.content().setElement("texture" + random.nextInt(50000),texture);
			
			texture.modDim().scale(random.nextFloat()).compile();
			
		}
	}

}
