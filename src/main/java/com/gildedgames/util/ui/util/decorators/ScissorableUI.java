package com.gildedgames.util.ui.util.decorators;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.common.UIDecorator;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class ScissorableUI extends UIDecorator<UIView>
{

	protected Dim2D scissoredArea;
	
	public ScissorableUI(Dim2D scissoredArea, UIView element)
	{
		super(element);
		
		this.scissoredArea = scissoredArea;
	}
	
	public Dim2D getScissoredArea()
	{
		return this.scissoredArea;
	}
	
	public void setScissoredArea(Dim2D dim)
	{
		this.scissoredArea = dim;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.setScissoredArea(Dim2D.build(this.getScissoredArea()).scale(this.getDim().getScale()).commit());
		
		GL11.glPushMatrix();

		float lowerLeftCornerY = this.getScissoredArea().getY() + this.getScissoredArea().getHeight();

		float cornerX = (this.getScissoredArea().getX() * input.getScaleFactor());
		float cornerY = (input.getScreenHeight() - lowerLeftCornerY) * input.getScaleFactor();

		float cutWidth = this.getScissoredArea().getWidth() * input.getScaleFactor();
		float cutHeight = this.getScissoredArea().getHeight() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);
		
		GL11.glScissor((int)cornerX, (int)cornerY, (int)cutWidth, (int)cutHeight);

		super.draw(graphics, input);

		GL11.glDisable(GL_SCISSOR_TEST);
		
		GL11.glPopMatrix();
	}

}
