package com.gildedgames.util.core;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class Sprite extends TextureAtlasSprite
{
	public int width, height;

	public Sprite(String iconName, int width, int height)
	{
		super(iconName);
		
		this.width = width;
		this.height = height;
	}

    public int getIconWidth()
    {
        return this.width;
    }

    public int getIconHeight()
    {
        return this.height;
    }
}
