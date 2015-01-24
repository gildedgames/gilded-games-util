package com.gildedgames.util.universe.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.core.Sprite;
import com.gildedgames.util.tab.common.util.ITab;

public abstract class TabGeneric implements ITab
{

	private static final Sprite sprite = new Sprite("", 16, 16);
	
	public TabGeneric()
	{
		sprite.initSprite(16, 16, 0, 0, false);
	}

	@Override
	public void renderIcon(int x, int y)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(this.getIconTexture());
		Gui.func_146110_a(x, y, 0, 0, sprite.getIconWidth(), sprite.getIconWidth(), sprite.getIconWidth(), sprite.getIconWidth());
	}
	
	public abstract ResourceLocation getIconTexture();

}
