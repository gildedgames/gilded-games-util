package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.ui.UIDimensions;
import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.util.UIGraphicsAbstract;

public class UITexture extends UIGraphicsAbstract<GraphicsMinecraft>
{
	
	private final ResourceLocation textureLocation;
	
	private Gui gui = new Gui();

	public UITexture(ResourceLocation textureLocation, double width, double height)
	{
		super(width, height);
		
		this.textureLocation = textureLocation;
	}

	@Override
	public void draw(GraphicsMinecraft graphics)
	{
		graphics.getMinecraft().renderEngine.bindTexture(this.textureLocation);
		this.gui.drawTexturedModalRect((int)this.getPosition().getX(), (int)this.getPosition().getY(), 0, 0, (int)this.getWidth(), (int)this.getHeight());
	}

	@Override
	public void init(UIElementHolder elementHolder, UIDimensions screenDimensions)
	{
		
	}

}