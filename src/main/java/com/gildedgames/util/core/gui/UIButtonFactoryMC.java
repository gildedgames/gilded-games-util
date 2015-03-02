package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.UIButton;
import com.gildedgames.util.ui.util.UITexture;

public class UIButtonFactoryMC
{
	
	private static final Resource HOPPER_TEXTURE = new Resource(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
	
	public UIBase createButtonWithSound(UIView button)
	{
		return new UIButtonSounds(button);
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
		
		Dimensions2D dim = new Dimensions2D().setPos(pos).setArea(20, 20).setCentered(centered);
		
		UIButton button = new UIButton(dim, new UITexture(buttonDefaultSprite, dim), new UITexture(buttonHoveredSprite, dim), new UITexture(buttonClickedSprite, dim));
		
		return this.createButtonWithSound(button);
	}
	
	public UIBase createButton(Position2D pos, int width, String text)
	{
		return this.createButton(pos, width, text, true);
	}
	
	public UIBase createButton(Position2D pos, int width, String text, boolean centered)
	{
		Dimensions2D dim = new Dimensions2D().setArea(width, 20).setCentered(centered).setPos(pos);
		
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
	
}