package com.gildedgames.util.ui.util;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class UISlider extends UIDecorator
{
	
	protected Minecraft mc = Minecraft.getMinecraft();

	protected Dimensions2D box;
	
	protected float totalHeight;
	
	public UISlider(Dimensions2D dim, UIElement element)
	{
		super(element);
		
		this.box = dim;
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		float sliderValue = 0.0F;

		this.totalHeight = this.getDimensions().getHeight();

		if (this.totalHeight > this.box.getHeight())
		{
			sliderValue = -this.slider.sliderValue * (this.totalHeight - this.box.getHeight());
		}

		double mouseFactor = this.slider.sliderValue * (this.totalHeight - this.box.getHeight());

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.slider.xPosition = this.box.getX() + (this.isRight ? this.box.getWidth() - 10 : 0);
		this.slider.yPosition = this.box.getY() + this.heightOffset;

		this.slider.drawButton(this.mc, input.getMouseX(), input.getMouseY());
		
		ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int scaleFactor = scaledRes.getScaleFactor();

		int scHeight = scaledRes.getScaledHeight();

		int yFactor = Math.abs(this.box.getY() + this.heightOffset - scHeight) - this.box.getHeight();

		float cornerX = this.box.getX() * scaleFactor;
		float cornerY = yFactor * scaleFactor;

		float scaledY = this.getDimensions().getY();

		float cutWidth = this.box.getWidth() * scaleFactor;
		float cutHeight = this.box.getWidth() * scaleFactor;

		GlStateManager.enableBooleanStateAt(GL_SCISSOR_TEST);
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		GlStateManager.pushMatrix();

		GlStateManager.translate(0.0F, sliderValue, 0.0F);

		super.draw(graphics, input);

		GlStateManager.popMatrix();
		GlStateManager.disableBooleanStateAt(GL_SCISSOR_TEST);
	}

}