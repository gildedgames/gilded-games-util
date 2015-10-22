package com.gildedgames.util.ui.util.input;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.input.InputProvider;

public abstract class RadioButton extends GuiFrame
{
	
	private boolean on;
	
	private final AssetLocation onTexture = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/radioButtonOn.png");
	
	private final AssetLocation offTexture = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/radioButtonOff.png");
	
	private GuiFrame onIcon, offIcon;
	
	public RadioButton()
	{
		this.onIcon = GuiFactory.createTexture(this.onTexture);
		this.offIcon = GuiFactory.createTexture(this.offTexture);
	}
	
	public abstract void turnedOn();
	
	public abstract void turnedOff();
	
	public void setOn(boolean on)
	{
		this.on = on;
		
		if (on)
		{
			this.turnedOn();
			
			this.onIcon.setVisible(true);
			this.offIcon.setVisible(false);
		}
		else
		{
			this.turnedOff();
			
			this.onIcon.setVisible(false);
			this.offIcon.setVisible(true);
		}
	}
	
	public boolean isOn()
	{
		return this.on;
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		this.content().set("onIcon", this.onIcon);
		this.content().set("offIcon", this.offIcon);
		
		this.dim().mod().area(this.onIcon.dim().width(), this.onIcon.dim().height()).flush();
	}
	
}
