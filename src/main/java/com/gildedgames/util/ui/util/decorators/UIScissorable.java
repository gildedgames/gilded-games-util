package com.gildedgames.util.ui.util.decorators;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class UIScissorable extends UIDecorator<UIElement>
{

	protected Dimensions2D scissoredArea;
	
	public UIScissorable(Dimensions2D scissoredArea, UIElement element)
	{
		super(element);
		
		this.scissoredArea = scissoredArea;
	}
	
	public Dimensions2D getScissoredArea()
	{
		return this.scissoredArea;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		float yFactor = Math.abs(this.getScissoredArea().getY() - input.getScreenHeight()) - this.getScissoredArea().getHeight();

		float cornerX = (this.getScissoredArea().getX() * input.getScaleFactor());
		float cornerY = yFactor * input.getScaleFactor();

		float cutWidth = this.getScissoredArea().getWidth() * input.getScaleFactor();
		float cutHeight = this.getScissoredArea().getHeight() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);
		
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		super.draw(graphics, input);

		GL11.glDisable(GL_SCISSOR_TEST);
	}

}
