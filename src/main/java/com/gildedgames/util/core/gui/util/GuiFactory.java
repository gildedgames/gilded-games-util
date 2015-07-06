package com.gildedgames.util.core.gui.util;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.decorators.MinecraftButtonSounds;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.Font;
import com.gildedgames.util.ui.util.ScrollBar;
import com.gildedgames.util.ui.util.Text;
import com.gildedgames.util.ui.util.TextBox;
import com.gildedgames.util.ui.util.TextureElement;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;

public class GuiFactory
{

	private static final MinecraftAssetLocation HOPPER_TEXTURE = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");

	private static final MinecraftAssetLocation SCROLL_BAR_TEXTURE = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/scrollBar.png");

	private GuiFactory()
	{

	}

	public static GuiFrame decorateWithPressSound(Gui button)
	{
		return new MinecraftButtonSounds(button);
	}

	public static GuiFrame createArrowButton()
	{
		return GuiFactory.createArrowButton(new Pos2D());
	}

	public static GuiFrame createArrowButton(boolean centered)
	{
		return GuiFactory.createArrowButton(new Pos2D(), centered);
	}

	public static GuiFrame createArrowButton(Pos2D pos)
	{
		return GuiFactory.createArrowButton(pos, true);
	}

	public static GuiFrame createArrowButton(Pos2D pos, boolean centered)
	{
		Sprite buttonDefaultSprite = new Sprite(HOPPER_TEXTURE, 0, 174, 20, 20, 256, 256);
		Sprite buttonHoveredSprite = new Sprite(HOPPER_TEXTURE, 20, 174, 20, 20, 256, 256);
		Sprite buttonClickedSprite = new Sprite(HOPPER_TEXTURE, 40, 174, 20, 20, 256, 256);

		Dim2D dim = Dim2D.build().pos(pos).area(20, 20).center(centered).compile();

		Button button = new Button(dim, new TextureElement(buttonDefaultSprite, dim), new TextureElement(buttonHoveredSprite, dim), new TextureElement(buttonClickedSprite, dim));

		return GuiFactory.decorateWithPressSound(button);
	}

	public static GuiFrame createButton(Pos2D pos, int width, String text)
	{
		return GuiFactory.createButton(pos, width, text, true);
	}

	public static GuiFrame createButton(Pos2D pos, int width, String text, boolean centered)
	{
		Dim2D dim = Dim2D.build().area(width, 20).center(centered).pos(pos).compile();

		Gui button = new MinecraftButton(dim, text);

		return GuiFactory.decorateWithPressSound(button);
	}

	public static GuiFrame createButton(GuiButton button)
	{
		return GuiFactory.createButton(button, true);
	}

	public static GuiFrame createButton(GuiButton button, boolean centered)
	{
		return GuiFactory.decorateWithPressSound(new MinecraftButton(button, centered));
	}

	public static ScrollBar createScrollBar(Pos2D pos, int height, Dim2D scrollableArea)
	{
		return GuiFactory.createScrollBar(pos, height, scrollableArea, true);
	}

	public static ScrollBar createScrollBar(Pos2D pos, int height, Dim2D scrollableArea, boolean centered)
	{
		Sprite bar = new Sprite(SCROLL_BAR_TEXTURE, 0, 0, 10, 10, 40, 10);
		Sprite base = new Sprite(SCROLL_BAR_TEXTURE, 10, 0, 10, 10, 40, 10);

		Dim2D spriteDimensions = Dim2D.build().area(10, 10).center(centered).compile();
		Dim2D barDimensions = Dim2D.build().area(10, height).center(centered).compile();

		GuiFrame topButton = GuiFactory.createArrowButton(centered);
		GuiFrame bottomButton = GuiFactory.createArrowButton(centered);

		ScrollBar scrollBar = new ScrollBar(barDimensions, topButton, bottomButton, new TextureElement(base, spriteDimensions), new TextureElement(bar, spriteDimensions));

		scrollBar.setScrollingAreas(new Dim2DCollection().addDim(scrollableArea));

		return scrollBar;
	}

	public static ScrollBar createScrollBar()
	{
		return GuiFactory.createScrollBar(new Pos2D(), 0, Dim2D.compile());
	}

	public static TextureElement createTexture(Sprite sprite, Dim2D dim)
	{
		return new TextureElement(sprite, dim);
	}

	public static TextureElement createTexture(AssetLocation asset, Dim2D dim)
	{
		return GuiFactory.createTexture(new Sprite(asset), dim);
	}

	public static TextureElement createTexture(AssetLocation asset)
	{
		return GuiFactory.createTexture(asset, Dim2D.compile());
	}

	public static Text text(String text, Color color)
	{
		return new Text(text, color, GuiFactory.font());
	}

	public static Text text(String text, Color color, float scale)
	{
		return new Text(text, color, scale, GuiFactory.font());
	}

	public static GuiFrame textBox(Dim2D dim, boolean hasSlider, Text... text)
	{
		TextBox box = new TextBox(dim, text);
		
		if (!hasSlider)
		{
			return box;
		}
		
		return new ScrollableGui(dim, box, GuiFactory.createScrollBar());
	}

	public static Font font()
	{
		final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		return new Font()
		{

			@Override
			public int getWidth(String text)
			{
				return fontRenderer.getStringWidth(text);
			}

			@Override
			public int getHeight(String text)
			{
				return fontRenderer.FONT_HEIGHT;
			}

			@Override
			public List<String> splitStringsIntoArea(String text, int width)
			{
				return fontRenderer.listFormattedStringToWidth(text, width);
			}
		};
	}
}
