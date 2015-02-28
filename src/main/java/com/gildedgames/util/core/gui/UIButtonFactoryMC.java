package com.gildedgames.util.core.gui;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UIOverhead;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.util.UIButton;
import com.gildedgames.util.ui.util.UITexture;

public class UIButtonFactoryMC
{
	
	private static final Resource HOPPER_TEXTURE = new Resource(UtilCore.MOD_ID, "textures/gui/universe_hopper/base.png");
	
	public UIOverhead createButtonWithSound(UIButton button)
	{
		return new UIButtonSounds(button);
	}
	
	public UIOverhead createArrowButton(Position2D pos)
	{
		Sprite buttonDefaultSprite = new Sprite(HOPPER_TEXTURE, 0, 174, 20, 20, 256, 256);
		Sprite buttonHoveredSprite = new Sprite(HOPPER_TEXTURE, 20, 174, 20, 20, 256, 256);
		Sprite buttonClickedSprite = new Sprite(HOPPER_TEXTURE, 40, 174, 20, 20, 256, 256);
		
		Dimensions2D dim = new Dimensions2D().set(pos).set(20, 20);
		
		UIButton button = new UIButton(dim, new UITexture(buttonDefaultSprite, dim), new UITexture(buttonHoveredSprite, dim), new UITexture(buttonClickedSprite, dim));
		
		return this.createButtonWithSound(button);
	}
	
}