package com.gildedgames.util.ui.graphics;

import com.gildedgames.util.ui.data.AssetLocation;


public class Sprite
{
	
	private final double u, v, width, height, textureWidth, textureHeight;

	private final AssetLocation asset;
	
	public Sprite(AssetLocation asset, double width, double height)
	{
		this(asset, width, height, width, height);
	}
	
	public Sprite(AssetLocation asset, double width, double height, double textureWidth, double textureHeight)
	{
		this(asset, 0, 0, width, height, textureWidth, textureHeight);
	}

	public Sprite(AssetLocation asset, double u, double v, double width, double height, double textureWidth, double textureHeight)
	{
		this.asset = asset;
		
		this.u = u;
		this.v = v;
		
		this.width = width;
		this.height = height;
		
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}
	
	public AssetLocation getAsset()
	{
		return this.asset;
	}

    public double getTextureWidth()
    {
        return this.textureWidth;
    }

    public double getTextureHeight()
    {
        return this.textureHeight;
    }

    public double getMinU()
    {
    	return this.u;
    }

    public double getMinV()
    {
    	return this.v;
    }
    
    public double getMaxU()
    {
        return this.getMinU() + this.getWidth();
    }

    public double getMaxV()
    {
        return this.getMinV() + this.getHeight();
    }
    
    public double getWidth()
    {
    	return this.width;
    }
    
    public double getHeight()
    {
    	return this.height;
    }
	
}
