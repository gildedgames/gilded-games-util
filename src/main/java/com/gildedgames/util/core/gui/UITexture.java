package com.gildedgames.util.core.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.ui.UIElementHolder;
import com.gildedgames.util.ui.util.UIDimensions;
import com.gildedgames.util.ui.util.UIGraphicsAbstract;

public class UITexture extends UIGraphicsAbstract<GraphicsMinecraft>
{

	private final ResourceLocation textureLocation;

	private Gui gui = new Gui();

	public UITexture(ResourceLocation textureLocation, UIDimensions dimensions)
	{
		super(dimensions);

		this.textureLocation = textureLocation;
	}

	@Override
	public void draw(GraphicsMinecraft graphics)
	{
		graphics.getMinecraft().renderEngine.bindTexture(this.textureLocation);
		UIDimensions dimensions = this.getDimensions();
		GlStateManager.pushMatrix();
		GlStateManager.scale(dimensions.getScale(), dimensions.getScale(), 1);
		this.gui.drawTexturedModalRect((int) dimensions.getX(), (int) dimensions.getY(), 0, 0, (int) dimensions.getWidth(), (int) dimensions.getHeight());
		GlStateManager.popMatrix();
	}

	@Override
	public void init(UIElementHolder elementHolder, UIDimensions screenDimensions)
	{

	}

	@Override
	public Class<? extends GraphicsMinecraft> getGraphicsClass()
	{
		return GraphicsMinecraft.class;
	}

}
