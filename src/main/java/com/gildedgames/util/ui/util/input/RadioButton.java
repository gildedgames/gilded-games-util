package com.gildedgames.util.ui.util.input;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.input.InputProvider;

public class RadioButton extends GuiFrame
{

	private boolean on;

	private GuiFrame onIcon, offIcon;

	public RadioButton()
	{
		this(new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/radioButtonOn.png"), new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/radioButtonOff.png"));
	}

	public RadioButton(AssetLocation onTexture, AssetLocation offTexture)
	{
		this.onIcon = GuiFactory.createTexture(onTexture);
		this.offIcon = GuiFactory.createTexture(offTexture);
	}

	public RadioButton(GuiFrame onFrame, GuiFrame offFrame)
	{
		this.onIcon = onFrame;
		this.offIcon = offFrame;
	}

	public void turnedOn()
	{

	}

	public void turnedOff()
	{

	}

	public void setOn(boolean on)
	{
		this.on = on;

		if (on)
		{
			this.turnedOn();

			this.onIcon.setVisible(true);
			this.offIcon.setVisible(false);
			this.dim().mod().area(this.onIcon.dim().width(), this.onIcon.dim().height()).flush();
		}
		else
		{
			this.turnedOff();

			this.onIcon.setVisible(false);
			this.offIcon.setVisible(true);
			this.dim().mod().area(this.offIcon.dim().width(), this.offIcon.dim().height()).flush();
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
