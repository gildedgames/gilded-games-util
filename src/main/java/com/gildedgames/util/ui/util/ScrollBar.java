package com.gildedgames.util.ui.util;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.common.BasicUI;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.event.view.MouseEventView;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.decorators.RepeatableUI;

public class ScrollBar extends AbstractUI
{

	protected BasicUI topButton, bottomButton;

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

	public ScrollBar(Dim2D barDim, Dim2DHolder scrollingArea, BasicUI topButton, BasicUI bottomButton, TextureElement baseTexture, TextureElement barTexture)
	{
		super(barDim);

		this.scrollingArea = scrollingArea;

		this.topButton = topButton;
		this.bottomButton = bottomButton;
		
		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topButton.getDimensions().withoutModifiers().getWidth(), this.bottomButton.getDimensions().withoutModifiers().getWidth()), this.baseBarTexture.getDimensions().getWidth());

		this.getDimensions().setWidth(maxWidth);
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
	public void onInit(UIElementContainer container, InputProvider input)
	{
		super.onInit(container, input);

		this.topButton.getDimensions().setPos(new Pos2D());
		this.bottomButton.getDimensions().setPos(new Pos2D());
		
		this.topButton.getDimensions().setCentering(this.getDimensions());
		this.bottomButton.getDimensions().setCentering(this.getDimensions());
		
		//Dimensions2DModifier totalHeightMinusBottomButton = new Dimensions2DModifier().addDim(new Dim2D().setY(this.getDimensions().getHeight() - this.bottomArrowButton.getDimensions().getHeight()));

		//this.bottomArrowButton.getDimensions().addModifier(totalHeightMinusBottomButton);

		this.topButton.getListeners().setElement("topButtonScrollEvent", new ButtonScrollEvent(this, 0.5F));
		
		this.bottomButton.getListeners().setElement("bottomButtonScrollEvent", new ButtonScrollEvent(this, -0.5F));

		container.setElement("topButton", this.topButton);
		container.setElement("bottomButton", this.bottomButton);

		this.baseBar = new RepeatableUI(this.baseBarTexture);
		this.grabbableBar = new RepeatableUI(this.grabbableBarTexture);
		
		Dim2DModifier bottomOfTopButton = new Dim2DModifier().addDim(new Dim2D().setY(this.topButton.getDimensions().withoutModifiers().getHeight()));
		
		//this.baseBar.getDimensions().addModifier(bottomOfTopButton);
		//this.grabbableBar.getDimensions().addModifier(bottomOfTopButton);
		
		this.baseBar.getDimensions().setArea(this.baseBarTexture.getDimensions().withoutModifiers().getWidth(), this.getDimensions().withoutModifiers().getHeight());
		this.grabbableBar.getDimensions().setArea(this.grabbableBarTexture.getDimensions().getWidth(), 20);

		container.setElement("baseBar", this.baseBar);
		container.setElement("grabbableBar", this.grabbableBar);
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.grabbableBar.getDimensions()) || input.isHovered(this.scrollingArea.getDimensions()))
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
			if (pool.has(ButtonState.PRESSED) && input.isHovered(this.baseBar.getDimensions()))
			{
				this.grabbedBar = true;
				
				if (input.isHovered(this.grabbableBar.getDimensions()))
				{
					this.grabbedMouseYOffset = -(this.grabbableBar.getDimensions().getY() - input.getMouseY());
				}
				else
				{
					this.grabbedMouseYOffset = this.grabbableBar.getDimensions().getHeight() / 2;
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
			int basePosY = input.getMouseY() - this.baseBar.getDimensions().getY() + this.grabbedMouseYOffset;
			
			this.setScrollPercentage((float)basePosY / (float)this.baseBar.getDimensions().getHeight());
		}
		
		final int grabbableBarY = this.baseBar.getDimensions().getY() + (int)((this.baseBar.getDimensions().getHeight() - this.baseBar.getDimensions().getY()) * this.getScrollPercentage());
		
		this.grabbableBar.getDimensions().setY(grabbableBarY);

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
		return this.scrollingArea.getDimensions();
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
			if (pool.has(MouseButton.LEFT) && input.isHovered(this.scrollBar.topButton.getDimensions()))
			{
				this.scrollBar.increaseScrollPercentage(this.scrollPercentage);
			}
		}

	}
	
}
