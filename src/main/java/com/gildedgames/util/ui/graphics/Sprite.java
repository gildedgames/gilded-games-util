package com.gildedgames.util.ui.graphics;

import com.gildedgames.util.ui.data.IResource;


public class Sprite
{
	
	private final double minU, minV, maxU, maxV, textureWidth, textureHeight;

	private final IResource resource;
	
	public Sprite(IResource resource, double minU, double minV, double maxU, double maxV, double textureWidth, double textureHeight)
	{
		this.resource = resource;
		
		this.minU = minU;
		this.minV = minV;
		
		this.maxU = maxU;
		this.maxV = maxV;
		
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}
	
	public IResource getResource()
	{
		return this.resource;
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
    	return this.minU;
    }

    public double getMinV()
    {
    	return this.minV;
    }
    
    public double getMaxU()
    {
        return this.maxU;
    }

    public double getMaxV()
    {
        return this.maxV;
    }
	
}
