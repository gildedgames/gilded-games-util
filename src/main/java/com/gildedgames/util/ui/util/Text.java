package com.gildedgames.util.ui.util;

import java.awt.Color;

import com.gildedgames.util.ui.data.DrawingData;

public class Text
{
	String text;

	float scale;

	DrawingData drawingData;

	Font font;

	public Text(String text, Color color, Font font)
	{
		this.drawingData = new DrawingData(color);
		this.text = text;
		this.scale = 1.0f;
		this.font = font;
	}

	public Text(String text, Color color, float scale, Font font)
	{
		this.drawingData = new DrawingData(color);
		this.text = text;
		this.scale = scale;
		this.font = font;
	}

	public String text()
	{
		return this.text;
	}

	public float scale()
	{
		return this.scale;
	}

	public DrawingData drawingData()
	{
		return this.drawingData;
	}

	public Font font()
	{
		return this.font;
	}

	public double width()
	{
		return this.font.getWidth(this.text);
	}

	public double height()
	{
		return this.font.getHeight(this.text);
	}

	public double scaledWidth()
	{
		return (int) (this.width() * this.scale);
	}

	public double scaledHeight()
	{
		return (int) (this.height() * this.scale);
	}
}
