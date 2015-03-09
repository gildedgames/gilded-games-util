package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.UIButton;
import com.gildedgames.util.ui.util.UIScrollBar;
import com.gildedgames.util.ui.util.UITexture;

public class UIButtonFactoryMC
{
	
	private static final Resource HOPPER_TEXTURE = new Resource(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
	
	private static final Resource SCROLL_BAR_TEXTURE = new Resource(UtilCore.MOD_ID, "textures/gui/test/scrollBar.png");
	
	public UIBase createButtonWithSound(UIView button)
	{
		return new UIButtonSounds(button);
	}
	
	public UIBase createArrowButton()
	{
		return this.createArrowButton(new Position2D(0, 0));
	}
	
	public UIBase createArrowButton(Position2D pos)
	{
		return this.createArrowButton(pos, true);
	}
	
	public UIBase createArrowButton(Position2D pos, boolean centered)
	{
		Sprite buttonDefaultSprite = new Sprite(HOPPER_TEXTURE, 0, 174, 20, 20, 256, 256);
		Sprite buttonHoveredSprite = new Sprite(HOPPER_TEXTURE, 20, 174, 20, 20, 256, 256);
		Sprite buttonClickedSprite = new Sprite(HOPPER_TEXTURE, 40, 174, 20, 20, 256, 256);
		
		Dimensions2D dim = new Dimensions2D().setPos(pos).setArea(20, 20).setCentering(centered);
		
		UIButton button = new UIButton(dim, new UITexture(buttonDefaultSprite, dim), new UITexture(buttonHoveredSprite, dim), new UITexture(buttonClickedSprite, dim));
		
		return this.createButtonWithSound(button);
	}
	
	public UIBase createButton(Position2D pos, int width, String text)
	{
		return this.createButton(pos, width, text, true);
	}
	
	public UIBase createButton(Position2D pos, int width, String text, boolean centered)
	{
		Dimensions2D dim = new Dimensions2D().setArea(width, 20).setCentering(centered).setPos(pos);
		
		UIView button = new UIButtonMinecraft(dim, text);
		
		return this.createButtonWithSound(button);
	}
	
	public UIBase createButton(GuiButton button)
	{
		return this.createButton(button, true);
	}
	
	public UIBase createButton(GuiButton button, boolean centered)
	{
		return this.createButtonWithSound(new UIButtonMinecraft(button, centered));
	}
	
	public UIBase createScrollBar(Position2D pos, int height)
	{
		return this.createScrollBar(pos, height, true);
	}
	
	public UIBase createScrollBar(Position2D pos, int height, boolean centered)
	{
		Sprite bar = new Sprite(SCROLL_BAR_TEXTURE, 0, 0, 10, 10, 40, 10);
		Sprite base = new Sprite(SCROLL_BAR_TEXTURE, 10, 0, 10, 10, 40, 10);
		Sprite upArrow = new Sprite(SCROLL_BAR_TEXTURE, 20, 0, 10, 10, 40, 10);
		Sprite downArrow = new Sprite(SCROLL_BAR_TEXTURE, 30, 0, 10, 10, 40, 10);
		
		Dimensions2D spriteDim = new Dimensions2D().copyWithPos(pos).copyWithArea(10, 10).copyWithCentering(centered);
		
		Dimensions2D dim = new Dimensions2D().copyWithArea(10, height).copyWithPos(pos);
		
		UIBase topButton = this.createArrowButton();
		UIBase bottomButton = this.createArrowButton();
		
		topButton.getDimensions().setScale(1F);
		bottomButton.getDimensions().setScale(1F);
		
		UIScrollBar scrollBar = new UIScrollBar(dim, dim.copyWithArea(50, height + 200), topButton, bottomButton, new UITexture(base, spriteDim), new UITexture(bar, spriteDim));
		
		return scrollBar;
	}
	
}