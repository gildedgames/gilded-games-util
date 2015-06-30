package com.gildedgames.util.ui.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.zip.ZipException;

import javax.imageio.ImageIO;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.data.AssetLocation;

public class Sprite
{

	private final double u, v, width, height, textureWidth, textureHeight;

	private final AssetLocation asset;

	public Sprite(AssetLocation asset)
	{
		this.asset = asset;

		this.u = 0;
		this.v = 0;

		int width = 0, height = 0, textureWidth = 0, textureHeight = 0;

		try
		{
			BufferedImage bufferedImage = ImageIO.read(UtilCore.locate().getStreamFromAsset(asset));

			width = bufferedImage.getWidth();
			height = bufferedImage.getHeight();

			textureWidth = width;
			textureHeight = height;
		}
		catch (ZipException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.width = width;
		this.height = height;

		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

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
