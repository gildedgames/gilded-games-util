package com.gildedgames.util.ui.util;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class UIScrollable extends UIDecorator
{
	
	protected Minecraft mc = Minecraft.getMinecraft();

	protected Dimensions2D box;
	
	protected int totalHeight, heightOffset;
	
	protected UIScrollBar scrollBar;
	
	protected InputProvider shiftedInput;
	
	protected int shiftedWidth;
	
	public UIScrollable(Dimensions2D dim, UIElement element, UIScrollBar scrollBar)
	{
		super(element);
		
		this.box = dim;
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		this.shiftedWidth = (int) this.scrollBar.getDimensions().getWidth();
		
		this.shiftedInput = input.copyWithMouseXOffset(this.shiftedWidth);
		
		super.init(container, this.shiftedInput);
		
		container.add(this.scrollBar);
		
		this.box.setX(this.scrollBar.getDimensions().getX() + this.shiftedWidth);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		float scrollValue = 0.0F;

		if (this.box.getHeight() > this.getDimensions().getHeight())
		{
			scrollValue = -this.scrollBar.getScrollPercentage() * (this.box.getHeight() - this.getDimensions().getHeight());
		}
		
		float yFactor = Math.abs(this.box.getY() + this.heightOffset - input.getScreenHeight()) - this.box.getHeight();

		float cornerX = (this.box.getX() * input.getScaleFactor());
		float cornerY = yFactor * input.getScaleFactor();

		float cutWidth = this.box.getWidth() * input.getScaleFactor();
		float cutHeight = this.box.getHeight() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);
		
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		GlStateManager.pushMatrix();

		GlStateManager.translate(this.shiftedWidth, scrollValue, 0.0F);
		
		this.shiftedInput = this.shiftedInput.copyWithMouseXOffset(this.shiftedWidth);
		this.shiftedInput = this.shiftedInput.copyWithMouseYOffset((int)scrollValue);

		super.draw(graphics, this.shiftedInput);

		GlStateManager.popMatrix();
		
		GL11.glDisable(GL_SCISSOR_TEST);
	}

}