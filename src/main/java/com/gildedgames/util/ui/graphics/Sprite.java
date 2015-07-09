package com.gildedgames.util.ui.graphics;

import java.awt.image.BufferedImage;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.data.AssetLocation;

public class Sprite
{

	private final int assetWidth, assetHeight;
	
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

	public int getAssetWidth()
	{
		return this.assetWidth;
	}

	public int getAssetHeight()
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
		
		private final int minU, minV, width, height;
		
		private UV(int minU, int minV, int width, int height)
		{
			this.minU = minU;
			this.minV = minV;
			
			this.width = width;
			this.height = height;
		}
		
		public int minU()
		{
			return this.minU;
		}
		
		public int minV()
		{
			return this.minV;
		}
		
		public int maxU()
		{
			return this.minU + this.width;
		}
		
		public int maxV()
		{
			return this.minV + this.height;
		}
		
		public int width()
		{
			return this.width;
		}
		
		public int height()
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
		
		private int minU, minV, width, height;
		
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
		
		public UVBuilder width(int width)
		{
			this.width = width;
			
			return this;
		}
		
		public UVBuilder height(int height)
		{
			this.height = height;
			
			return this;
		}
		
		public UVBuilder min(int minU, int minV)
		{
			this.minU = minU;
			this.minV = minV;
			
			return this;
		}
		
		public UVBuilder max(int maxU, int maxV)
		{
			this.width = maxU - this.minU;
			this.height = maxV - this.minV;
			
			return this;
		}
		
		public UVBuilder area(int width, int height)
		{
			return this.width(width).height(height);
		}
		
		public UVBuilder minU(int minU)
		{
			this.minU = minU;
			
			return this;
		}
		
		public UVBuilder addMinU(int minU)
		{
			this.minU += minU;
			
			return this;
		}
		
		public UVBuilder minV(int minV)
		{
			this.minV = minV;
			
			return this;
		}
		
		public UVBuilder addMinV(int minV)
		{
			this.minV += minV;
			
			return this;
		}
		
		public UVBuilder maxU(int maxU)
		{
			this.width = maxU - this.minU;
			
			return this;
		}
		
		public UVBuilder addMaxU(int maxU)
		{
			this.width += maxU - this.minU;
			
			return this;
		}
		
		public UVBuilder maxV(int maxV)
		{
			this.height += maxV - this.minV;
			
			return this;
		}
		
		public UVBuilder addMaxV(int maxV)
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
