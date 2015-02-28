package com.gildedgames.util.ui.graphics;

import com.gildedgames.util.ui.data.IResource;


public class Sprite
{
	
	private final double u, v, width, height, textureWidth, textureHeight;

	private final IResource resource;
	
	public Sprite(IResource resource, double width, double height)
	{
		this(resource, width, height, width, height);
	}
	
	public Sprite(IResource resource, double width, double height, double textureWidth, double textureHeight)
	{
		this(resource, 0, 0, width, height, textureWidth, textureHeight);
	}

	public Sprite(IResource resource, double u, double v, double width, double height, double textureWidth, double textureHeight)
	{
		this.resource = resource;
		
		this.u = u;
		this.v = v;
		
		this.width = width;
		this.height = height;
		
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
