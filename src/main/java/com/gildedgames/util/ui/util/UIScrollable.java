package com.gildedgames.util.ui.util;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class UIScrollable extends UIDecorator
{
	
	protected Minecraft mc = Minecraft.getMinecraft();

	protected Dimensions2D box;
	
	protected int totalHeight, heightOffset;
	
	public UIScrollable(Dimensions2D dim, UIElement element)
	{
		super(element);
		
		this.box = dim;
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		float sliderValue = 0.0F;

		this.totalHeight = (int) this.getDimensions().getHeight();

		if (this.totalHeight > this.box.getHeight())
		{
			sliderValue = -this.slider.sliderValue * (this.totalHeight - this.box.getHeight());
		}

		double mouseFactor = this.slider.sliderValue * (this.totalHeight - this.box.getHeight());

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.slider.xPosition = this.box.getX();
		this.slider.yPosition = this.box.getY() + this.heightOffset;

		this.slider.drawButton(this.mc, input.getMouseX(), input.getMouseY());
		
		float yFactor = Math.abs(this.box.getY() + this.heightOffset - input.getScreenHeight()) - this.box.getHeight();

		float cornerX = this.box.getX() * input.getScaleFactor();
		float cornerY = yFactor * input.getScaleFactor();

		float cutWidth = this.box.getWidth() * input.getScaleFactor();
		float cutHeight = this.box.getWidth() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);
		
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		GlStateManager.pushMatrix();

		GlStateManager.translate(0.0F, sliderValue, 0.0F);

		super.draw(graphics, input);

		GlStateManager.popMatrix();
		
		GL11.glDisable(GL_SCISSOR_TEST);
	}

}