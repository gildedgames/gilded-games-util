package com.gildedgames.util.group.client;

import java.awt.Color;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.input.RadioButton;

public class RadioButtonDefault extends RadioButton
{

	private final static AssetLocation texture = new MinecraftAssetLocation(UtilModule.MOD_ID, "textures/gui/test/inputBox.png");

	public RadioButtonDefault(int width, int height)
	{
		super(new RectangleElement(Dim2D.build().area(width, height).flush(), new DrawingData(new Color(0xFF60B36A)), new DrawingData(new Color(0xFF4F8F56))),
				new RectangleElement(Dim2D.build().area(width, height).flush(), new DrawingData(new Color(0xFF555555)), new DrawingData(new Color(0xFF666666))));
	}
}
