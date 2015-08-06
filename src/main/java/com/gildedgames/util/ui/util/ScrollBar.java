package com.gildedgames.util.ui.util;

import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.ModifierType;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DGetter;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.Dim2DSeeker;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.decorators.RepeatableGui;
import com.google.common.collect.ImmutableList;

public class ScrollBar extends GuiFrame
{

	protected GuiFrame topButton, bottomButton;

	/**
	 * The two textures used in the scrollbar. They are
	 * repeated to make longer bars.
	 */
	protected TextureElement baseBarTexture, grabbableBarTexture;

	protected RepeatableGui baseBar, grabbableBar;

	protected Dim2DCollection scrollingAreas;

	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	protected float scrollPercentage = 0.0F, scrollSpeed = 0.02F;

	protected int grabbedMouseYOffset;

	protected Dim2DHolder contentArea;

	public ScrollBar(Dim2D barDim, GuiFrame topButton, GuiFrame bottomButton, TextureElement baseTexture, TextureElement barTexture)
	{
		super(barDim);

		this.topButton = topButton;
		this.bottomButton = bottomButton;

		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topButton.copyDim().clearModifiers().flush().width(), this.bottomButton.copyDim().clearModifiers().flush().width()), this.baseBarTexture.getDim().width());

		this.modDim().width(maxWidth).flush();
	}

	public void setScrollSpeed(float scrollSpeed)
	{
		this.scrollSpeed = scrollSpeed;
	}

	public float getScrollSpeed()
	{
		return this.scrollSpeed;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.topButton.modDim().center(false).resetPos().flush();
		this.bottomButton.modDim().center(false).resetPos().flush();

		this.baseBarTexture.modDim().center(false).flush();
		this.grabbableBarTexture.modDim().center(false).flush();

		this.topButton.listeners().set("topButtonScrollEvent", new ButtonScrollEvent(this.topButton, this, -0.01F));
		this.bottomButton.listeners().set("bottomButtonScrollEvent", new ButtonScrollEvent(this.bottomButton, this, 0.01F));

		this.baseBar = new RepeatableGui(Dim2D.build()
				.area(this.baseBarTexture.getDim().width(), this.getDim().height() - this.topButton.getDim().height() - this.bottomButton.getDim().height())
				.flush(), this.baseBarTexture);

		this.grabbableBar = new RepeatableGui(Dim2D.build().area(this.grabbableBarTexture.getDim().width(), 20).flush(), this.grabbableBarTexture);

		Dim2DSeeker totalHeightMinusBottomButton = new Dim2DGetter()
		{

			@Override
			public Dim2D getDim()
			{
				return Dim2D.build().y(ScrollBar.this.topButton.getDim().height() + ScrollBar.this.baseBar.getDim().height()).flush();
			}

		};

		Dim2DSeeker topButtonHeight = new Dim2DGetter()
		{

			@Override
			public Dim2D getDim()
			{
				return Dim2D.build().y(ScrollBar.this.topButton.getDim().height()).flush();
			}

		};

		this.bottomButton.modDim().addModifier(totalHeightMinusBottomButton, ModifierType.POS).flush();

		this.baseBar.modDim().addModifier(topButtonHeight, ModifierType.POS).flush();
		this.grabbableBar.modDim().addModifier(topButtonHeight, ModifierType.POS).flush();

		this.content().set("baseBar", this.baseBar);
		this.content().set("grabbableBar", this.grabbableBar);

		this.content().set("topButton", this.topButton);
		this.content().set("bottomButton", this.bottomButton);
		
		super.initContent(input);
	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
	{
		super.onMouseScroll(scrollDifference, input);

		if (input.isHovered(this.grabbableBar.getDim()) || input.isHovered(this.scrollingAreas))
		{
			int scrollFactor = -scrollDifference / 120;

			this.increaseScrollPercentage(scrollFactor * this.scrollSpeed);
		}
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		super.onMouseInput(pool, input);

		if (this.grabbedBar && !pool.has(ButtonState.PRESSED))
		{
			this.grabbedBar = false;
		}

		if (pool.has(MouseButton.LEFT))
		{
			if (pool.has(ButtonState.PRESSED) && input.isHovered(this.baseBar.getDim()))
			{
				this.grabbedBar = true;

				if (input.isHovered(this.grabbableBar.getDim()))
				{
					this.grabbedMouseYOffset = this.grabbableBar.getDim().y() - input.getMouseY();
				}
				else
				{
					this.grabbedMouseYOffset = -(this.grabbableBar.getDim().height() / 2) + 1;
				}
			}
			else if (pool.has(ButtonState.RELEASED))
			{
				this.grabbedBar = false;
			}
		}
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (this.scrollingAreas != null && this.contentArea != null)
		{
			int contentAndScrollHeightDif = Math.abs(this.contentArea.getDim().height() - this.scrollingAreas.getDim().height());

			float baseBarPercentage = (float) contentAndScrollHeightDif / (float) this.contentArea.getDim().height();
			
			int barHeight = this.baseBar.getDim().height() - (int) (this.baseBar.getDim().height() * baseBarPercentage);

			this.grabbableBar.modDim().height(Math.max(10, barHeight)).flush();
		}

		if (this.grabbedBar)
		{
			int basePosY = input.getMouseY() - this.baseBar.getDim().y() + this.grabbedMouseYOffset;

			float percent = (float) basePosY / (float) (this.baseBar.getDim().height() - this.grabbableBar.getDim().height());

			this.setScrollPercentage(percent);
		}

		int posOnBar = (int) ((this.baseBar.getDim().height() - this.grabbableBar.getDim().height()) * this.getScrollPercentage());

		this.grabbableBar.modDim().y(posOnBar).flush();

		super.draw(graphics, input);
	}

	private void setScrollPercentage(float percentage)
	{
		this.scrollPercentage = percentage;

		this.scrollPercentage = Math.max(0.0F, Math.min(this.scrollPercentage, 1.0F));
	}

	private void increaseScrollPercentage(float percentage)
	{
		this.setScrollPercentage(this.getScrollPercentage() + percentage);
	}

	/**
	 * @return A float value between 0.0F - 1.0F which represents the position of the grabbed bar on the scroll base.
	 */
	public float getScrollPercentage()
	{
		return this.scrollPercentage;
	}

	public void setScrollingAreas(Dim2DCollection scrollingAreas)
	{
		this.scrollingAreas = scrollingAreas;
	}

	public ImmutableList<Dim2DHolder> getScrollingAreas()
	{
		return this.scrollingAreas.getDimHolders();
	}

	public void setContentArea(Dim2DHolder contentArea)
	{
		this.contentArea = contentArea;
	}

	public Dim2DHolder getContentArea()
	{
		return this.contentArea;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setFloat("scrollPercentage", this.scrollPercentage);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.scrollPercentage = input.getFloat("scrollPercentage");
	}

	public static class ButtonScrollEvent extends MouseEventGui
	{

		private Dim2DHolder button;

		private ScrollBar scrollBar;

		private float scrollPercentage;

		public ButtonScrollEvent(Dim2DHolder button, ScrollBar scrollBar, float scrollPercentage)
		{
			super(scrollBar);

			this.button = button;
			this.scrollBar = scrollBar;
			this.scrollPercentage = scrollPercentage;
		}

		@Override
		public void tick(TickInfo tickInfo, InputProvider input)
		{
			if (Mouse.isButtonDown(MouseButton.LEFT.getIndex()) && input.isHovered(this.button))
			{
				this.scrollBar.increaseScrollPercentage(this.scrollPercentage);
			}
			
			super.tick(tickInfo, input);
		}

		@Override
		protected void onTrue(InputProvider input, MouseInputPool pool)
		{
			
		}

		@Override
		protected void onFalse(InputProvider input, MouseInputPool pool)
		{
			
		}

	}

}
