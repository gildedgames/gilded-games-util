package com.gildedgames.util.ui.util.decorators;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DSeekable;
import com.gildedgames.util.ui.data.Dim2DSeeker;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.google.common.collect.ImmutableList;

public class ScissorableGui extends GuiDecorator<Gui> implements Dim2DSeekable
{

	protected Dim2D scissoredArea;

	private ImmutableList seekers = ImmutableList.<Dim2DSeeker> of(new Seeker(this));

	public ScissorableGui(Dim2D scissoredArea, Gui gui)
	{
		super(gui);

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
	protected void preInitContent(InputProvider input)
	{
		
	}

	@Override
	protected void postInitContent(InputProvider input)
	{
		
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		GL11.glPushMatrix();

		float lowerLeftCornerY = this.getScissoredArea().y() + this.getScissoredArea().height();

		float cornerX = (this.getScissoredArea().x() * input.getScaleFactor());
		float cornerY = (input.getScreenHeight() - lowerLeftCornerY) * input.getScaleFactor();

		float cutWidth = this.getScissoredArea().width() * input.getScaleFactor();
		float cutHeight = this.getScissoredArea().height() * input.getScaleFactor();

		GL11.glEnable(GL_SCISSOR_TEST);

		GL11.glScissor((int) cornerX, (int) cornerY, (int) cutWidth, (int) cutHeight);

		super.draw(graphics, input);

		GL11.glDisable(GL_SCISSOR_TEST);

		GL11.glPopMatrix();
	}

	@Override
	public ImmutableList<Dim2DSeeker> getDimSeekers()
	{
		return this.seekers;
	}

	public static class Seeker extends Dim2DSeeker<ScissorableGui>
	{

		public Seeker()
		{

		}

		public Seeker(ScissorableGui seekFrom)
		{
			super(seekFrom);
		}

		@Override
		public Dim2D getDim()
		{
			return this.seekFrom.getScissoredArea();
		}

		@Override
		public void setDim(Dim2D dim)
		{
			this.seekFrom.setScissoredArea(dim);
		}

	}

}
