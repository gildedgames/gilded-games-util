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
	
	public UIScrollable(Dimensions2D dim, UIElement element, UIScrollBar scrollBar)
	{
		super(element);
		
		this.box = dim;
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);
		
		container.add(this.scrollBar);
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		/*float scrollValue = 0.0F;

		if (this.getDimensions().getHeight() > this.box.getHeight())
		{
			scrollValue = -this.scrollBar.getScrollPercentage() * (this.getDimensions().getHeight() - this.box.getHeight());
		}
		
		float yFactor = Math.abs(this.box.getY() + this.heightOffset - input.getScreenHeight()) - this.box.getHeight();

		float cornerX = this.box.getX() * input.getScaleFactor();
		float cornerY = yFactor * input.getScaleFactor();

		float cutWidth = this.box.getWidth() * input.getScaleFactor();
		float cutHeight = this.box.getWidth() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);
		
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		GlStateManager.pushMatrix();

		GlStateManager.translate(0.0F, scrollValue, 0.0F);*/

		super.draw(graphics, input);

		/*GlStateManager.popMatrix();
		
		GL11.glDisable(GL_SCISSOR_TEST);*/
	}

}