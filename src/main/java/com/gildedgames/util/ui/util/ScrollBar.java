package com.gildedgames.util.ui.util;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DCollection;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.event.view.MouseEventView;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.decorators.RepeatableUI;
import com.google.common.collect.ImmutableList;

public class ScrollBar extends UIFrame
{

	protected UIFrame topButton, bottomButton;

	/**
	 * The two textures used in the scrollbar. They are
	 * repeated to make longer bars.
	 */
	protected TextureElement baseBarTexture, grabbableBarTexture;
	
	protected RepeatableUI baseBar, grabbableBar;

	protected Dim2DCollection scrollingAreas;
	
	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	protected float scrollPercentage = 0.0F, scrollSpeed = 0.002F;

	protected int grabbedMouseYOffset;

	public ScrollBar(Dim2D barDim, UIFrame topButton, UIFrame bottomButton, TextureElement baseTexture, TextureElement barTexture)
	{
		super(barDim);

		this.topButton = topButton;
		this.bottomButton = bottomButton;
		
		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topButton.copyDim().clearModifiers().compile().getWidth(), this.bottomButton.copyDim().clearModifiers().compile().getWidth()), this.baseBarTexture.getDim().getWidth());

		this.modDim().width(maxWidth).compile();
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
	public void init(InputProvider input)
	{
		super.init(input);

		this.topButton.modDim().resetPos().compile();
		this.bottomButton.modDim().resetPos().compile();
		
		this.topButton.modDim().center(this.getDim()).compile();
		this.bottomButton.modDim().center(this.getDim()).compile();
		
		this.baseBarTexture.modDim().center(this.getDim()).compile();
		this.grabbableBarTexture.modDim().center(this.getDim()).compile();
		
		Dim2DCollection totalHeightMinusBottomButton = new Dim2DCollection().addDim(Dim2D.build().y(this.getDim().getHeight() - this.bottomButton.getDim().getHeight()).compile());

		this.bottomButton.modDim().addModifier(totalHeightMinusBottomButton).compile();

		this.topButton.listeners().setElement("topButtonScrollEvent", new ButtonScrollEvent(this, 0.5F));
		
		this.bottomButton.listeners().setElement("bottomButtonScrollEvent", new ButtonScrollEvent(this, -0.5F));

		this.content().setElement("topButton", this.topButton);
		this.content().setElement("bottomButton", this.bottomButton);

		this.baseBar = new RepeatableUI(Dim2D.build().area(this.baseBarTexture.getDim().getWidth(), this.copyDim().clearModifiers().compile().getHeight()).compile(), this.baseBarTexture);
		this.grabbableBar = new RepeatableUI(Dim2D.build().area(this.grabbableBarTexture.getDim().getWidth(), 20).compile(), this.grabbableBarTexture);
		
		Dim2DCollection bottomOfTopButton = new Dim2DCollection().addDim(Dim2D.build().y(this.topButton.copyDim().clearModifiers().compile().getHeight()).compile());
		
		this.baseBar.modDim().addModifier(bottomOfTopButton).compile();
		this.grabbableBar.modDim().addModifier(bottomOfTopButton).compile();

		this.content().setElement("baseBar", this.baseBar);
		this.content().setElement("grabbableBar", this.grabbableBar);
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.grabbableBar.getDim()) || input.isHovered(this.scrollingAreas))
		{
			int scrollFactor = -scrollDifference / 120;
			
			this.increaseScrollPercentage(scrollFactor * this.scrollSpeed);
		}
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		super.onMouseInput(input, pool);

		if (this.grabbedBar && !pool.has(ButtonState.DOWN))
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
					this.grabbedMouseYOffset = (this.grabbableBar.getDim().getY() - input.getMouseY());
				}
				else
				{
					this.grabbedMouseYOffset = -(this.grabbableBar.getDim().getHeight() / 2) + 1;
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
		if (this.getScrollingAreas() != null)
		{
			/** TODO: Change grabbable bar's height to be proportional to height of content **/ 
			//this.grabbableBar.getDimensions().setHeight(this.getContentDimensions().getHeight() / this.baseBar.getDimensions().getHeight());
		}
		
		if (this.grabbedBar)
		{
			int basePosY = input.getMouseY() - this.baseBar.getDim().getY() + this.grabbedMouseYOffset;
			
			float percent = (float)basePosY / (float)(this.baseBar.getDim().getHeight() - this.grabbableBar.getDim().getHeight());
			
			this.setScrollPercentage(percent);
		}
		
		int posOnBar = (int)((this.baseBar.getDim().getHeight() - this.grabbableBar.getDim().getHeight()) * this.getScrollPercentage());

		this.grabbableBar.modDim().y(posOnBar).compile();
		
		super.draw(graphics, input);
	}
	
	private void setScrollPercentage(float percentage)
	{
		this.scrollPercentage = percentage;
		
		this.scrollPercentage = Math.max(0.0F, (Math.min(this.scrollPercentage, 1.0F)));
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
	
	public static class ButtonScrollEvent extends MouseEventView
	{
		
		private ScrollBar scrollBar;

		private float scrollPercentage;
		
		public ButtonScrollEvent(ScrollBar scrollBar, float scrollPercentage)
		{
			super(scrollBar);
			
			this.scrollBar = scrollBar;
			this.scrollPercentage = scrollPercentage;
		}

		@Override
		public void onMouseInput(InputProvider input, MouseInputPool pool)
		{
			if (pool.has(MouseButton.LEFT) && input.isHovered(this.scrollBar.topButton.getDim()))
			{
				this.scrollBar.increaseScrollPercentage(this.scrollPercentage);
			}
		}

	}
	
}
