package com.gildedgames.util.core.gui.util;

import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.decorators.MinecraftButtonSounds;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftDefaultButton;
import com.gildedgames.util.ui.common.BasicUI;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.Dim2DCollection;
import com.gildedgames.util.ui.util.ScrollBar;
import com.gildedgames.util.ui.util.TextureElement;

public class UIFactory
{
	
	private static final MinecraftAssetLocation HOPPER_TEXTURE = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
	
	private static final MinecraftAssetLocation SCROLL_BAR_TEXTURE = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/scrollBar.png");
	
	private UIFactory()
	{
		
	}
	
	public static BasicUI decorateWithPressSound(UIView button)
	{
		return new MinecraftButtonSounds(button);
	}
	
	public static BasicUI createArrowButton()
	{
		return UIFactory.createArrowButton(new Pos2D());
	}
	
	public static BasicUI createArrowButton(boolean centered)
	{
		return UIFactory.createArrowButton(new Pos2D(), centered);
	}
	
	public static BasicUI createArrowButton(Pos2D pos)
	{
		return UIFactory.createArrowButton(pos, true);
	}
	
	public static BasicUI createArrowButton(Pos2D pos, boolean centered)
	{
		Sprite buttonDefaultSprite = new Sprite(HOPPER_TEXTURE, 0, 174, 20, 20, 256, 256);
		Sprite buttonHoveredSprite = new Sprite(HOPPER_TEXTURE, 20, 174, 20, 20, 256, 256);
		Sprite buttonClickedSprite = new Sprite(HOPPER_TEXTURE, 40, 174, 20, 20, 256, 256);
		
		Dim2D dim = Dim2D.build().pos(pos).area(20, 20).center(centered).commit();
		
		Button button = new Button(dim, new TextureElement(buttonDefaultSprite, dim.clone()), new TextureElement(buttonHoveredSprite, dim.clone()), new TextureElement(buttonClickedSprite, dim.clone()));
		
		return UIFactory.decorateWithPressSound(button);
	}
	
	public static BasicUI createButton(Pos2D pos, int width, String text)
	{
		return UIFactory.createButton(pos, width, text, true);
	}
	
	public static BasicUI createButton(Pos2D pos, int width, String text, boolean centered)
	{
		Dim2D dim = Dim2D.build().area(width, 20).center(centered).pos(pos).commit();
		
		UIView button = new MinecraftDefaultButton(dim, text);
		
		return UIFactory.decorateWithPressSound(button);
	}
	
	public static BasicUI createButton(GuiButton button)
	{
		return UIFactory.createButton(button, true);
	}
	
	public static BasicUI createButton(GuiButton button, boolean centered)
	{
		return UIFactory.decorateWithPressSound(new MinecraftDefaultButton(button, centered));
	}
	
	public static ScrollBar createScrollBar(Pos2D pos, int height, Dim2D scrollableArea)
	{
		return UIFactory.createScrollBar(pos, height, scrollableArea, true);
	}
	
	public static ScrollBar createScrollBar(Pos2D pos, int height, Dim2D scrollableArea, boolean centered)
	{
		Sprite bar = new Sprite(SCROLL_BAR_TEXTURE, 0, 0, 10, 10, 40, 10);
		Sprite base = new Sprite(SCROLL_BAR_TEXTURE, 10, 0, 10, 10, 40, 10);
		
		Dim2D spriteDimensions = Dim2D.build().area(10, 10).center(centered).commit();
		Dim2D barDimensions = Dim2D.build().area(10, height).center(centered).commit();
		
		BasicUI topButton = UIFactory.createArrowButton(centered);
		BasicUI bottomButton = UIFactory.createArrowButton(centered);
		
		ScrollBar scrollBar = new ScrollBar(barDimensions, new Dim2DCollection().addDim(scrollableArea), topButton, bottomButton, new TextureElement(base, spriteDimensions.clone()), new TextureElement(bar, spriteDimensions.clone()));

		return scrollBar;
	}
	
	public static ScrollBar createScrollBar()
	{
		return UIFactory.createScrollBar(new Pos2D(), 0, new Dim2D());
	}

}