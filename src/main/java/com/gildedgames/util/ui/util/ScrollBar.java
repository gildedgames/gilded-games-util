package com.gildedgames.util.ui.util;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.common.UIFrame;
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

public class ScrollBar extends UIFrame
{

	protected UIFrame topButton, bottomButton;

	/**
	 * The two textures used in the scrollbar. They are
	 * repeated to make longer bars.
	 */
	protected TextureElement baseBarTexture, grabbableBarTexture;
	
	protected RepeatableUI baseBar, grabbableBar;

	protected Dim2DHolder scrollingArea;
	
	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	protected float scrollPercentage = 0.0F, scrollSpeed = 0.002F;

	protected int grabbedMouseYOffset;

	public ScrollBar(Dim2D barDim, Dim2DHolder scrollingArea, UIFrame topButton, UIFrame bottomButton, TextureElement baseTexture, TextureElement barTexture)
	{
		super(barDim);

		this.scrollingArea = scrollingArea;

		this.topButton = topButton;
		this.bottomButton = bottomButton;
		
		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topButton.copyDim().clearModifiers().commit().getWidth(), this.bottomButton.copyDim().clearModifiers().commit().getWidth()), this.baseBarTexture.getDim().getWidth());

		this.modDim().width(maxWidth).commit();
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

		this.topButton.modDim().resetPos().commit();
		this.bottomButton.modDim().resetPos().commit();
		
		this.topButton.modDim().center(this.getDim()).commit();
		this.bottomButton.modDim().center(this.getDim()).commit();
		
		this.baseBarTexture.modDim().center(this.getDim()).commit();
		this.grabbableBarTexture.modDim().center(this.getDim()).commit();
		
		Dim2DCollection totalHeightMinusBottomButton = new Dim2DCollection().addDim(Dim2D.build().y(this.getDim().getHeight() - this.bottomButton.getDim().getHeight()).commit());

		this.bottomButton.modDim().addModifier(totalHeightMinusBottomButton).commit();

		this.topButton.listeners().setElement("topButtonScrollEvent", new ButtonScrollEvent(this, 0.5F));
		
		this.bottomButton.listeners().setElement("bottomButtonScrollEvent", new ButtonScrollEvent(this, -0.5F));

		this.content().setElement("topButton", this.topButton);
		this.content().setElement("bottomButton", this.bottomButton);

		this.baseBar = new RepeatableUI(Dim2D.build().area(this.baseBarTexture.getDim().getWidth(), this.copyDim().clearModifiers().commit().getHeight()).commit(), this.baseBarTexture);
		this.grabbableBar = new RepeatableUI(Dim2D.build().area(this.grabbableBarTexture.getDim().getWidth(), 20).commit(), this.grabbableBarTexture);
		
		Dim2DCollection bottomOfTopButton = new Dim2DCollection().addDim(Dim2D.build().y(this.topButton.copyDim().clearModifiers().commit().getHeight()).commit());
		
		this.baseBar.modDim().addModifier(bottomOfTopButton).commit();
		this.grabbableBar.modDim().addModifier(bottomOfTopButton).commit();

		this.content().setElement("baseBar", this.baseBar);
		this.content().setElement("grabbableBar", this.grabbableBar);
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.grabbableBar.getDim()) || input.isHovered(this.scrollingArea.getDim()))
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
					this.grabbedMouseYOffset = -(this.grabbableBar.getDim().getY() - input.getMouseY());
				}
				else
				{
					this.grabbedMouseYOffset = this.grabbableBar.getDim().getHeight() / 2;
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
		if (this.getScrollingArea() != null)
		{
			/** TODO: Change grabbable bar's height to be proportional to height of content **/ 
			//this.grabbableBar.getDimensions().setHeight(this.getContentDimensions().getHeight() / this.baseBar.getDimensions().getHeight());
		}
		
		if (this.grabbedBar)
		{
			int basePosY = input.getMouseY() - this.baseBar.getDim().getY() + this.grabbedMouseYOffset;
			
			this.setScrollPercentage((float)basePosY / (float)this.baseBar.getDim().getHeight());
		}
		
		final int grabbableBarY = this.baseBar.getDim().getY() + (int)((this.baseBar.getDim().getHeight() - this.baseBar.getDim().getY()) * this.getScrollPercentage());
		
		this.grabbableBar.setDim(Dim2D.build(this.grabbableBar).y(grabbableBarY).commit());

		//System.out.println(this.baseBar.getDimensions().containsModifier(this.grabbableBar));
		
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
	
	public void setScrollingArea(Dim2DHolder contentDimensions)
	{
		this.scrollingArea = contentDimensions;
	}
	
	public Dim2D getScrollingArea()
	{
		return this.scrollingArea.getDim();
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
