package com.gildedgames.util.core.gui.util;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.util.decorators.MinecraftButtonSounds;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.data.AssetLocation;
import com.gildedgames.util.modules.ui.data.Pos2D;
import com.gildedgames.util.modules.ui.data.rect.Dim2D;
import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.graphics.ResizableUVBehavior;
import com.gildedgames.util.modules.ui.graphics.Sprite;
import com.gildedgames.util.modules.ui.graphics.Sprite.UV;
import com.gildedgames.util.modules.ui.util.Button;
import com.gildedgames.util.modules.ui.util.Font;
import com.gildedgames.util.modules.ui.util.ScrollBar;
import com.gildedgames.util.modules.ui.util.Text;
import com.gildedgames.util.modules.ui.util.TextBox;
import com.gildedgames.util.modules.ui.util.TextureElement;
import com.gildedgames.util.modules.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.modules.ui.util.rect.RectCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.util.List;

public class GuiFactory
{

	private static final MinecraftAssetLocation SCROLL_BAR = new MinecraftAssetLocation(UtilModule.MOD_ID, "textures/gui/test/scrollBar.png");

	private static final MinecraftAssetLocation PANEL = new MinecraftAssetLocation(UtilModule.MOD_ID, "textures/gui/test/resizablePane.png");

	private static final MinecraftAssetLocation PANEL_EMBEDDED = new MinecraftAssetLocation(UtilModule.MOD_ID, "textures/gui/test/resizablePaneEmbedded.png");

	private GuiFactory()
	{

	}

	/**
	 * Decorates with a generic Minecraft press sound upon left-clicking.
	 * @param button
	 * @return
	 */
	public static GuiFrame pressSound(Gui button)
	{
		return new MinecraftButtonSounds(button);
	}

	public static GuiFrame upArrowButton()
	{
		return GuiFactory.downArrowButton(Dim2D.flush());
	}

	public static GuiFrame upArrowButton(Rect dim)
	{
		Sprite buttonDefaultSprite = Sprite.create(SCROLL_BAR, UV.build().minU(20).area(10, 10).flush());
		Sprite buttonHoveredSprite = Sprite.create(SCROLL_BAR, UV.build().minU(30).area(10, 10).flush());
		Sprite buttonClickedSprite = Sprite.create(SCROLL_BAR, UV.build().minU(40).area(10, 10).flush());

		Rect newDim = Dim2D.build().pos(dim.x(), dim.y()).area(10, 10).center(dim.isCenteredX(), dim.isCenteredY()).flush();

		Button button = new Button(newDim, new TextureElement(buttonDefaultSprite, newDim), new TextureElement(buttonHoveredSprite, newDim), new TextureElement(buttonClickedSprite, newDim));

		return GuiFactory.pressSound(button);
	}

	public static GuiFrame downArrowButton()
	{
		return GuiFactory.downArrowButton(Dim2D.flush());
	}

	public static GuiFrame downArrowButton(Rect dim)
	{
		Sprite buttonDefaultSprite = Sprite.create(SCROLL_BAR, UV.build().minU(50).area(10, 10).flush());
		Sprite buttonHoveredSprite = Sprite.create(SCROLL_BAR, UV.build().minU(60).area(10, 10).flush());
		Sprite buttonClickedSprite = Sprite.create(SCROLL_BAR, UV.build().minU(70).area(10, 10).flush());

		Rect newDim = Dim2D.build().pos(dim.x(), dim.y()).area(10, 10).center(dim.isCenteredX(), dim.isCenteredY()).flush();

		Button button = new Button(newDim, new TextureElement(buttonDefaultSprite, newDim), new TextureElement(buttonHoveredSprite, newDim), new TextureElement(buttonClickedSprite, newDim));

		return GuiFactory.pressSound(button);
	}

	public static GuiFrame button(Pos2D pos, float width, String text)
	{
		return GuiFactory.button(pos, width, text, true);
	}

	public static GuiFrame button(Pos2D pos, float width, String text, boolean centered)
	{
		Rect dim = Dim2D.build().area(width, 20).center(centered).pos(pos.x(), pos.y()).flush();

		Gui button = new MinecraftButton(dim, text);

		return GuiFactory.pressSound(button);
	}

	public static GuiFrame button(GuiButton button)
	{
		return GuiFactory.button(button, true);
	}

	public static GuiFrame button(GuiButton button, boolean centered)
	{
		return GuiFactory.pressSound(new MinecraftButton(button, centered));
	}

	public static ScrollBar scrollBar(Pos2D pos, float height, Rect scrollableArea)
	{
		return GuiFactory.scrollBar(pos, height, scrollableArea, true);
	}

	public static ScrollBar scrollBar(Pos2D pos, float height, Rect scrollableArea, boolean centered)
	{
		Sprite bar = Sprite.create(SCROLL_BAR, UV.build().min(0, 0).area(10, 10).flush());
		Sprite base = Sprite.create(SCROLL_BAR, UV.build().min(10, 0).area(10, 10).flush());

		Rect spriteDimensions = Dim2D.build().area(10, 10).center(centered).flush();
		Rect barDimensions = Dim2D.build().area(10, height).center(centered).flush();

		GuiFrame topButton = GuiFactory.upArrowButton(Dim2D.build().center(centered).flush());
		GuiFrame bottomButton = GuiFactory.downArrowButton(Dim2D.build().center(centered).flush());

		ScrollBar scrollBar = new ScrollBar(barDimensions, topButton, bottomButton, new TextureElement(base, spriteDimensions), new TextureElement(bar, spriteDimensions));

		scrollBar.setScrollingAreas(RectCollection.build().addDim(scrollableArea).flush());

		return scrollBar;
	}

	public static TextureElement panel(Rect dim)
	{
		return GuiFactory.createResizableTexture(PANEL, dim, UV.build().area(4, 4).flush(), UV.build().area(4, 20).flush(), UV.build().area(20, 4).flush());
	}

	public static TextureElement panelEmbedded(Rect dim)
	{
		return GuiFactory.createResizableTexture(PANEL_EMBEDDED, dim, UV.build().area(4, 4).flush(), UV.build().area(4, 20).flush(), UV.build().area(20, 4).flush());
	}

	public static ScrollBar createScrollBar()
	{
		return GuiFactory.scrollBar(Pos2D.flush(), 0, Dim2D.flush());
	}

	public static TextureElement streamedTexture(Sprite sprite)
	{
		return new TextureElement(sprite, Dim2D.flush());
	}

	public static TextureElement texture(Sprite sprite)
	{
		return new TextureElement(sprite, Dim2D.flush());
	}

	public static TextureElement texture(Sprite sprite, Rect dim)
	{
		return new TextureElement(sprite, dim);
	}

	public static TextureElement texture(AssetLocation asset)
	{
		return GuiFactory.texture(asset, Dim2D.flush());
	}

	public static TextureElement texture(AssetLocation asset, UV uv)
	{
		Sprite sprite = Sprite.create(asset, uv);

		return GuiFactory.texture(sprite, Dim2D.build().area(uv.width(), uv.height()).flush());
	}

	public static TextureElement texture(AssetLocation asset, Rect dim)
	{
		Sprite sprite = Sprite.create(asset);

		return GuiFactory.texture(sprite, dim.rebuild().area(sprite.getAssetWidth(), sprite.getAssetHeight()).flush());
	}

	public static TextureElement textureSizable(AssetLocation asset, Rect dim)
	{
		Sprite sprite = Sprite.create(asset, true);

		return GuiFactory.texture(sprite, dim.rebuild().area(sprite.getAssetWidth(), sprite.getAssetHeight()).flush());
	}

	public static TextureElement createResizableTexture(AssetLocation asset, Rect dim, UV corners, UV verticalSides, UV horizontalSides)
	{
		return GuiFactory.texture(Sprite.create(asset, new ResizableUVBehavior(corners, verticalSides, horizontalSides)), dim);
	}

	public static TextureElement createTexture(AssetLocation asset)
	{
		return GuiFactory.texture(asset, Dim2D.flush());
	}

	public static Text text(String text, Color color)
	{
		return new Text(text, color, GuiFactory.font());
	}

	public static Text text(String text, Color color, float scale)
	{
		return new Text(text, color, scale, GuiFactory.font());
	}

	public static GuiFrame textBox(Rect dim, boolean hasSlider, Text... text)
	{
		TextBox box = new TextBox(dim, false, text);

		if (!hasSlider)
		{
			return box;
		}

		return new ScrollableGui(dim, box, GuiFactory.createScrollBar());
	}

	public static GuiFrame centeredTextBox(Rect dim, boolean hasSlider, Text... text)
	{
		TextBox box = new TextBox(dim, true, text);

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
			public float getWidth(String text)
			{
				return fontRenderer.getStringWidth(text);
			}

			@Override
			public float getHeight(String text)
			{
				return fontRenderer.FONT_HEIGHT;
			}

			@Override
			public List<String> splitStringsIntoArea(String text, float width)
			{
				return fontRenderer.listFormattedStringToWidth(text, (int) width);
			}
		};
	}
}
