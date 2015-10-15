package com.gildedgames.util.ui.util;

import org.lwjgl.input.Mouse;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectHolder;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.decorators.RepeatableGui;
import com.gildedgames.util.ui.util.rect.RectCollection;
import com.gildedgames.util.ui.util.rect.RectGetter;
import com.gildedgames.util.ui.util.rect.RectSeeker;

import net.minecraft.nbt.NBTTagCompound;

public class ScrollBar extends GuiFrame
{

	protected GuiFrame topButton, bottomButton;

	/**
	 * The two textures used in the scrollbar. They are
	 * repeated to make longer bars.
	 */
	protected TextureElement baseBarTexture, grabbableBarTexture;

	protected RepeatableGui baseBar, grabbableBar;

	protected RectCollection scrollingAreas;

	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	protected float scrollPercentage = 0.0F, scrollSpeed = 0.02F;

	protected double grabbedMouseYOffset;

	protected RectHolder contentArea;

	public ScrollBar(Rect barDim, GuiFrame topButton, GuiFrame bottomButton, TextureElement baseTexture, TextureElement barTexture)
	{
		super(barDim);

		this.topButton = topButton;
		this.bottomButton = bottomButton;

		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		float maxWidth = Math.max(Math.max(this.topButton.dim().clone().clear().width(), this.bottomButton.dim().clone().clear().width()), this.baseBarTexture.dim().width());

		this.dim().mod().width(maxWidth).flush();
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
		this.topButton.dim().mod().center(false).resetPos().flush();
		this.bottomButton.dim().mod().center(false).resetPos().flush();

		this.baseBarTexture.dim().mod().center(false).flush();
		this.grabbableBarTexture.dim().mod().center(false).flush();

		this.topButton.events().set("topButtonScrollEvent", new ButtonScrollEvent(this.topButton, this, -0.01F));
		this.bottomButton.events().set("bottomButtonScrollEvent", new ButtonScrollEvent(this.bottomButton, this, 0.01F));

		this.baseBar = new RepeatableGui(Dim2D.build()
				.area(this.baseBarTexture.dim().width(), this.dim().height() - this.topButton.dim().height() - this.bottomButton.dim().height())
				.flush(), this.baseBarTexture);

		this.grabbableBar = new RepeatableGui(Dim2D.build().area(this.grabbableBarTexture.dim().width(), 20).flush(), this.grabbableBarTexture);

		RectSeeker totalHeightMinusBottomButton = new RectGetter()
		{

			@Override
			public Rect assembleRect()
			{
				return Dim2D.build().y(ScrollBar.this.topButton.dim().height() + ScrollBar.this.baseBar.dim().height()).flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				return true;
			}

		};

		RectSeeker topButtonHeight = new RectGetter()
		{

			@Override
			public Rect assembleRect()
			{
				return Dim2D.build().y(ScrollBar.this.topButton.dim().height()).flush();
			}

			@Override
			public boolean shouldReassemble()
			{
				return true;
			}

		};

		this.bottomButton.dim().add(totalHeightMinusBottomButton, ModifierType.POS);

		this.baseBar.dim().add(topButtonHeight, ModifierType.POS);
		this.grabbableBar.dim().add(topButtonHeight, ModifierType.POS);

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

		if (input.isHovered(this.grabbableBar.dim()) || input.isHovered(this.scrollingAreas))
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
			if (pool.has(ButtonState.PRESSED) && input.isHovered(this.baseBar.dim()))
			{
				this.grabbedBar = true;

				if (input.isHovered(this.grabbableBar.dim()))
				{
					this.grabbedMouseYOffset = this.grabbableBar.dim().y() - input.getMouseY();
				}
				else
				{
					this.grabbedMouseYOffset = -(this.grabbableBar.dim().height() / 2) + 1;
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
			double contentAndScrollHeightDif = Math.abs(this.contentArea.dim().height() - this.scrollingAreas.dim().height());

			float baseBarPercentage = this.contentArea.dim().height() < this.dim().height() ? 0f : (float) contentAndScrollHeightDif / this.contentArea.dim().height();

			float barHeight = this.baseBar.dim().height() - (int) (this.baseBar.dim().height() * baseBarPercentage);

			this.grabbableBar.dim().mod().height(Math.max(10, barHeight)).flush();
		}

		if (this.grabbedBar)
		{
			double basePosY = input.getMouseY() - this.baseBar.dim().y() + this.grabbedMouseYOffset;

			float percent = this.contentArea.dim().height() < this.dim().height() ? 0f : (float) basePosY / (this.baseBar.dim().height() - this.grabbableBar.dim().height());

			this.setScrollPercentage(percent);
		}

		int posOnBar = (int) ((this.baseBar.dim().height() - this.grabbableBar.dim().height()) * this.getScrollPercentage());

		this.grabbableBar.dim().mod().y(posOnBar).flush();

		super.draw(graphics, input);
	}

	private void setScrollPercentage(float percentage)
	{
		this.scrollPercentage = this.contentArea.dim().height() < this.dim().height() ? 0f : Math.max(0.0F, Math.min(percentage, 1.0F));

		int i = 0;
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

	public void setScrollingAreas(RectCollection scrollingAreas)
	{
		this.scrollingAreas = scrollingAreas;
	}

	public void setContentArea(RectHolder contentArea)
	{
		this.contentArea = contentArea;
	}

	public RectHolder getContentArea()
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

		private RectHolder button;

		private ScrollBar scrollBar;

		private float scrollPercentage;

		public ButtonScrollEvent(RectHolder button, ScrollBar scrollBar, float scrollPercentage)
		{
			super();

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

		@Override
		public void initEvent()
		{

		}

	}

}
