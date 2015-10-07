package com.gildedgames.util.ui.graphics;

import java.awt.image.BufferedImage;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.data.AssetLocation;

public class Sprite
{

	private final float assetWidth, assetHeight;
	
	private final UV uv;

	private final AssetLocation asset;
	
	private final UVBehavior behavior;
	
	public Sprite(AssetLocation asset)
	{
		this(asset, new DefaultUVBehavior());
	}

	public Sprite(AssetLocation asset, UVBehavior behavior)
	{
		this.asset = asset;

		BufferedImage image = UtilCore.locate().getBufferedImage(asset);

		this.uv = UV.build().width(image.getWidth()).height(image.getHeight()).flush();

		this.assetWidth = image.getWidth();
		this.assetHeight = image.getHeight();
		
		this.behavior = behavior;
	}
	
	public Sprite(AssetLocation asset, UV uv)
	{
		this(asset, uv, new DefaultUVBehavior());
	}

	public Sprite(AssetLocation asset, UV uv, UVBehavior behavior)
	{
		this.asset = asset;

		this.uv = uv;
		
		BufferedImage image = UtilCore.locate().getBufferedImage(asset);

		this.assetWidth = image.getWidth();
		this.assetHeight = image.getHeight();
		
		this.behavior = behavior;
	}

	public AssetLocation getAsset()
	{
		return this.asset;
	}

	public float getAssetWidth()
	{
		return this.assetWidth;
	}

	public float getAssetHeight()
	{
		return this.assetHeight;
	}

	public UV getUV()
	{
		return this.uv;
	}
	
	public UVBehavior getBehavior()
	{
		return this.behavior;
	}
	
	public static class UV
	{
		
		private final float minU, minV, width, height;
		
		private UV(float minU, float minV, float width, float height)
		{
			this.minU = minU;
			this.minV = minV;
			
			this.width = width;
			this.height = height;
		}
		
		public float minU()
		{
			return this.minU;
		}
		
		public float minV()
		{
			return this.minV;
		}
		
		public float maxU()
		{
			return this.minU + this.width;
		}
		
		public float maxV()
		{
			return this.minV + this.height;
		}
		
		public float width()
		{
			return this.width;
		}
		
		public float height()
		{
			return this.height;
		}
		
		public UVBuilder clone()
		{
			return new UVBuilder(this);
		}
		
		public static UVBuilder build()
		{
			return new UVBuilder();
		}
		
		public static UV flushEmpty()
		{
			return new UV(0, 0, 0, 0);
		}

	}
	
	public static class UVBuilder
	{
		
		private float minU, minV, width, height;
		
		private UVBuilder()
		{
			
		}
		
		private UVBuilder(UV uv)
		{
			this.minU = uv.minU;
			this.minV = uv.minV;
			
			this.width = uv.width;
			this.height = uv.height;
		}
		
		public UVBuilder width(float width)
		{
			this.width = width;
			
			return this;
		}
		
		public UVBuilder height(float height)
		{
			this.height = height;
			
			return this;
		}
		
		public UVBuilder min(float minU, float minV)
		{
			this.minU = minU;
			this.minV = minV;
			
			return this;
		}
		
		public UVBuilder max(float maxU, float maxV)
		{
			this.width = maxU - this.minU;
			this.height = maxV - this.minV;
			
			return this;
		}
		
		public UVBuilder area(float width, float height)
		{
			return this.width(width).height(height);
		}
		
		public UVBuilder minU(float minU)
		{
			this.minU = minU;
			
			return this;
		}
		
		public UVBuilder addMinU(float minU)
		{
			this.minU += minU;
			
			return this;
		}
		
		public UVBuilder minV(float minV)
		{
			this.minV = minV;
			
			return this;
		}
		
		public UVBuilder addMinV(float minV)
		{
			this.minV += minV;
			
			return this;
		}
		
		public UVBuilder maxU(float maxU)
		{
			this.width = maxU - this.minU;
			
			return this;
		}
		
		public UVBuilder addMaxU(float maxU)
		{
			this.width += maxU - this.minU;
			
			return this;
		}
		
		public UVBuilder maxV(float maxV)
		{
			this.height += maxV - this.minV;
			
			return this;
		}
		
		public UVBuilder addMaxV(float maxV)
		{
			this.height += maxV - minV;
			
			return this;
		}
		
		public UV flush()
		{
			return new UV(this.minU, this.minV, this.width, this.height);
		}
		
	}

}
