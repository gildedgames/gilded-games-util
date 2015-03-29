package com.gildedgames.util.ui.util.decorators;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIScissorable extends UIDecorator<UIElement>
{

	protected Dimensions2D cutoff;
	
	public UIScissorable(Dimensions2D cutoff, UIElement element)
	{
		super(element);
		
		this.cutoff = cutoff;
	}
	
	public Dimensions2D getCutoff()
	{
		return this.cutoff;
	}
	
	public void setCutoff(Dimensions2D cutoff)
	{
		this.cutoff = cutoff;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		float yFactor = Math.abs(this.cutoff.getY() - input.getScreenHeight()) - this.cutoff.getHeight();

		float cornerX = (this.cutoff.getX() * input.getScaleFactor());
		float cornerY = yFactor * input.getScaleFactor();

		float cutWidth = this.cutoff.getWidth() * input.getScaleFactor();
		float cutHeight = this.cutoff.getHeight() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);
		
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		super.draw(graphics, input);

		GL11.glDisable(GL_SCISSOR_TEST);
	}

}
