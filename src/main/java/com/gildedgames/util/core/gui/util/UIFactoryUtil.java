package com.gildedgames.util.core.gui.util;

import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.decorators.UIPressSoundsMC;
import com.gildedgames.util.core.gui.util.wrappers.UIButtonMC;
import com.gildedgames.util.ui.UIBasic;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.UIButton;
import com.gildedgames.util.ui.util.UIScrollBar;
import com.gildedgames.util.ui.util.UITexture;
import com.gildedgames.util.ui.util.decorators.UIRepeatable;
import com.gildedgames.util.ui.util.decorators.UIScrollable;

public class UIFactoryUtil
{
	
	private static final AssetMinecraft HOPPER_TEXTURE = new AssetMinecraft(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
	
	private static final AssetMinecraft SCROLL_BAR_TEXTURE = new AssetMinecraft(UtilCore.MOD_ID, "textures/gui/test/scrollBar.png");
	
	public UIBasic decorateWithPressSound(UIView button)
	{
		return new UIPressSoundsMC(button);
	}
	
	public UIBasic createArrowButton()
	{
		return this.createArrowButton(new Position2D());
	}
	
	public UIBasic createArrowButton(boolean centered)
	{
		return this.createArrowButton(new Position2D(), centered);
	}
	
	public UIBasic createArrowButton(Position2D pos)
	{
		return this.createArrowButton(pos, true);
	}
	
	public UIBasic createArrowButton(Position2D pos, boolean centered)
	{
		Sprite buttonDefaultSprite = new Sprite(HOPPER_TEXTURE, 0, 174, 20, 20, 256, 256);
		Sprite buttonHoveredSprite = new Sprite(HOPPER_TEXTURE, 20, 174, 20, 20, 256, 256);
		Sprite buttonClickedSprite = new Sprite(HOPPER_TEXTURE, 40, 174, 20, 20, 256, 256);
		
		Dimensions2D dim = new Dimensions2D().setPos(pos.clone()).setArea(20, 20).setCentering(centered);
		
		UIButton button = new UIButton(dim, new UITexture(buttonDefaultSprite, dim.clone()), new UITexture(buttonHoveredSprite, dim.clone()), new UITexture(buttonClickedSprite, dim.clone()));
		
		return this.decorateWithPressSound(button);
	}
	
	public UIBasic createButton(Position2D pos, int width, String text)
	{
		return this.createButton(pos, width, text, true);
	}
	
	public UIBasic createButton(Position2D pos, int width, String text, boolean centered)
	{
		Dimensions2D dim = new Dimensions2D().setArea(width, 20).setCentering(centered).setPos(pos.clone());
		
		UIView button = new UIButtonMC(dim, text);
		
		return this.decorateWithPressSound(button);
	}
	
	public UIBasic createButton(GuiButton button)
	{
		return this.createButton(button, true);
	}
	
	public UIBasic createButton(GuiButton button, boolean centered)
	{
		return this.decorateWithPressSound(new UIButtonMC(button, centered));
	}
	
	public UIScrollBar createScrollBar(Position2D pos, int height, Dimensions2D scrollableArea)
	{
		return this.createScrollBar(pos, height, scrollableArea, true);
	}
	
	public UIScrollBar createScrollBar(Position2D pos, int height, Dimensions2D scrollableArea, boolean centered)
	{
		Sprite bar = new Sprite(SCROLL_BAR_TEXTURE, 0, 0, 10, 10, 40, 10);
		Sprite base = new Sprite(SCROLL_BAR_TEXTURE, 10, 0, 10, 10, 40, 10);
		
		Dimensions2D spriteDimensions = new Dimensions2D().setArea(10, 10).setPos(pos.clone()).setCentering(centered);
		Dimensions2D barDimensions = new Dimensions2D().setArea(10, height).setPos(pos.clone()).setCentering(centered);
		
		UIBasic topButton = this.createArrowButton(centered);
		UIBasic bottomButton = this.createArrowButton(centered);
		
		UIScrollBar scrollBar = new UIScrollBar(barDimensions, scrollableArea, topButton, bottomButton, new UITexture(base, spriteDimensions.clone()), new UITexture(bar, spriteDimensions.clone()));

		return scrollBar;
	}

}