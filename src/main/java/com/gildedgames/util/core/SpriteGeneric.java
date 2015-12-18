package com.gildedgames.util.core;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SpriteGeneric extends TextureAtlasSprite
{

	public SpriteGeneric(String iconName, int width, int height)
	{
		super(iconName);
		
		this.width = width;
		this.height = height;
	}

}
