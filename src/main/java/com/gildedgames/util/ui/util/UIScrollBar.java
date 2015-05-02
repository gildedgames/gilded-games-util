package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIBasic;
import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.event.view.MouseEventView;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.decorators.UIRepeatable;

public class UIScrollBar extends UIBasicAbstract
{

	/**
	 * Rectangle describing the dimensions of the frame this
	 * scrollbar is mounted to 
	 */
	protected Dimensions2D scrollingArea;

	protected UIBasic topArrowButton, bottomArrowButton;

	/**
	 * The two textures used in the scrollbar. They are
	 * repeated to make longer bars.
	 */
	protected UITexture baseBarTexture, grabbableBarTexture;
	
	protected UIRepeatable baseBar, grabbableBar;

	protected Dimensions2D contentDimensions;
	
	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	protected float scrollPercentage = 0.0F, scrollSpeed = 0.075F;

	protected int grabbedMouseYOffset;

	public UIScrollBar(Dimensions2D barDim, Dimensions2D scrollingArea, UIBasic topArrowButton, UIBasic bottomArrowButton, UITexture baseTexture, UITexture barTexture)
	{
		super(barDim);

		this.scrollingArea = scrollingArea;

		this.topArrowButton = topArrowButton;
		this.bottomArrowButton = bottomArrowButton;
		
		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topArrowButton.getDimensions().withoutOrigin().getWidth(), this.bottomArrowButton.getDimensions().withoutOrigin().getWidth()), this.baseBarTexture.getDimensions().getWidth());

		this.getDimensions().setWidth(maxWidth);
	}
	
	public Dimensions2D getScrollingArea()
	{
		return this.scrollingArea;
	}
	
	public void setScrollingArea(Dimensions2D scrollingArea)
	{
		this.scrollingArea = scrollingArea;
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
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);

		this.topArrowButton.getDimensions().resetPos();
		this.bottomArrowButton.getDimensions().resetPos();
		
		this.topArrowButton.getDimensions().setCentering(this.getDimensions());
		this.bottomArrowButton.getDimensions().setCentering(this.getDimensions());
		
		final int originHeightValue = this.getDimensions().getHeight() - this.bottomArrowButton.getDimensions().getHeight();
		
		OriginHolder totalHeightMinusBottomButton = new OriginHolder(new Dimensions2D().setHeight(originHeightValue).setY(originHeightValue));

		this.bottomArrowButton.getDimensions().setOrigin(totalHeightMinusBottomButton);

		this.topArrowButton.getListeners().add(new MouseEventView(this)
		{

			@Override
			public void onMouseInput(InputProvider input, MouseInputPool pool)
			{
				if (pool.has(MouseButton.LEFT) && input.isHovered(UIScrollBar.this.topArrowButton.getDimensions()))
				{
					UIScrollBar.this.scrollPercentage -= UIScrollBar.this.scrollSpeed / 2;
				}
			}
		
		});
		
		this.bottomArrowButton.getListeners().add(new MouseEventView(this)
		{

			@Override
			public void onMouseInput(InputProvider input, MouseInputPool pool)
			{
				if (pool.has(MouseButton.LEFT) && input.isHovered(UIScrollBar.this.bottomArrowButton.getDimensions()))
				{
					UIScrollBar.this.scrollPercentage += UIScrollBar.this.scrollSpeed / 2;
				}
			}
		
		});

		container.add(this.topArrowButton);
		container.add(this.bottomArrowButton);

		this.baseBar = new UIRepeatable(this.baseBarTexture);
		this.grabbableBar = new UIRepeatable(this.grabbableBarTexture);
		
		OriginHolder bottomOfTopButton = new OriginHolder(new Dimensions2D().setY(this.topArrowButton.getDimensions().withoutOrigin().getHeight()));
		
		this.baseBar.getDimensions().setOrigin(bottomOfTopButton);
		this.grabbableBar.getDimensions().setOrigin(bottomOfTopButton);

		final int totalHeightMinusButtons = this.getDimensions().getHeight() - this.bottomArrowButton.getDimensions().getHeight() - this.topArrowButton.getDimensions().getHeight();
		
		this.baseBar.getDimensions().setArea(this.baseBarTexture.getDimensions().withoutOrigin().getWidth(), totalHeightMinusButtons);
		this.grabbableBar.getDimensions().setArea(this.grabbableBarTexture.getDimensions().getWidth(), 20);

		container.add(this.baseBar);
		container.add(this.grabbableBar);
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.grabbableBar.getDimensions()) || input.isHovered(this.scrollingArea))
		{
			int scrollFactor = -scrollDifference / 120;
			
			this.scrollPercentage += scrollFactor * this.scrollSpeed;
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
		if (this.getContentDimensions() != null)
		{
			/** TODO: Change grabbable bar's height to be proportional to height of content **/ 
			//this.grabbableBar.getDimensions().setHeight(this.getContentDimensions().getHeight() / this.baseBar.getDimensions().getHeight());
		}
		
		if (this.grabbedBar)
		{
			int basePosY = input.getMouseY() - this.grabbableBar.getDimensions().withoutOrigin().getHeight() + this.grabbedMouseYOffset;
			
			this.scrollPercentage = (float)basePosY / (float)this.baseBar.getDimensions().withoutOrigin().getHeight();
		}
		
		this.scrollPercentage = this.getScrollPercentage();
		
		final int grabbableBarY = (int)((this.baseBar.getDimensions().getHeight() - this.grabbableBar.getDimensions().getHeight()) * this.getScrollPercentage());
		
		this.grabbableBar.getDimensions().setY(grabbableBarY);

		super.draw(graphics, input);
	}

	/**
	 * @return A float value between 0.0F - 1.0F which represents the position of the grabbed bar on the scroll base.
	 */
	public float getScrollPercentage()
	{
		return Math.min(1.0F, Math.max(0.0F, this.scrollPercentage));
	}
	
	public void setContentDimensions(Dimensions2D contentDimensions)
	{
		this.contentDimensions = contentDimensions;
	}
	
	public Dimensions2D getContentDimensions()
	{
		return this.contentDimensions;
	}
	
}
