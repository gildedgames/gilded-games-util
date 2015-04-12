package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.UIBasic;
import com.gildedgames.util.ui.UIBasicAbstract;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
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

	protected float scrollSpeed = 0.075F;

	protected int grabbedMouseYOffset;

	public UIScrollBar(Dimensions2D barDim, Dimensions2D scrollingArea, UIBasic topArrowButton, UIBasic bottomArrowButton, UITexture baseTexture, UITexture barTexture)
	{
		super(barDim);

		this.scrollingArea = scrollingArea;

		this.topArrowButton = topArrowButton;
		this.bottomArrowButton = bottomArrowButton;
		
		this.baseBarTexture = baseTexture;
		this.grabbableBarTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topArrowButton.getDimensions().relative().getWidth(), this.bottomArrowButton.getDimensions().relative().getWidth()), this.baseBarTexture.getDimensions().getWidth());

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

		this.topArrowButton.getDimensions().setPos(new Position2D());
		this.bottomArrowButton.getDimensions().setPos(new Position2D());
		
		this.topArrowButton.getDimensions().setCentering(this.getDimensions());
		this.bottomArrowButton.getDimensions().setCentering(this.getDimensions());

		this.bottomArrowButton.getDimensions().setY(this.getDimensions().relative().getHeight() - this.bottomArrowButton.getDimensions().relative().getHeight());

		this.topArrowButton.getListeners().add(new MouseEventView(this)
		{

			@Override
			public void onMouseInput(InputProvider input, MouseInputPool pool)
			{
				UIScrollBar scrollBar = ObjectFilter.getType(this.view, UIScrollBar.class);

				if (pool.has(MouseButton.LEFT) && input.isHovered(scrollBar.topArrowButton.getDimensions()))
				{
					scrollBar.grabbableBar.getDimensions().addY(-5);
					scrollBar.snapBarToProportions();
				}
			}
		
		});
		
		this.bottomArrowButton.getListeners().add(new MouseEventView(this)
		{

			@Override
			public void onMouseInput(InputProvider input, MouseInputPool pool)
			{
				UIScrollBar scrollBar = ObjectFilter.getType(this.view, UIScrollBar.class);
				
				if (input.isHovered(scrollBar.bottomArrowButton.getDimensions()))
				{
					scrollBar.grabbableBar.getDimensions().addY(5);
					scrollBar.snapBarToProportions();
				}
			}
		
		});

		container.add(this.topArrowButton);
		container.add(this.bottomArrowButton);

		int heightOffset = this.bottomArrowButton.getDimensions().getHeight() + this.topArrowButton.getDimensions().relative().getHeight() - 1;

		this.baseBar = new UIRepeatable(this.baseBarTexture);
		this.grabbableBar = new UIRepeatable(this.grabbableBarTexture);
		
		this.baseBar.getDimensions().addY(this.topArrowButton.getDimensions().relative().getHeight());
		this.grabbableBar.getDimensions().addY(this.topArrowButton.getDimensions().relative().getHeight());
		
		this.baseBar.getDimensions().setArea(this.baseBarTexture.getDimensions().relative().getWidth(), this.getDimensions().relative().getHeight() - heightOffset);
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
			this.grabbableBar.getDimensions().addY((int)(-scrollDifference * this.scrollSpeed));
			this.snapBarToProportions();
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
					this.grabbedMouseYOffset = this.grabbableBar.getDimensions().getY() - input.getMouseY();
				}
				else
				{
					this.grabbedMouseYOffset = -this.grabbableBar.getDimensions().getHeight() / 2;
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
		this.refreshProportions(input);

		super.draw(graphics, input);
		
		//System.out.println(this.bottomArrowButton.getDimensions().getOrigin().getDimensions());
	}
	
	/**
	 * @return A float value between 0.0F - 1.0F which represents the position of the grabbed bar on the scroll base.
	 */
	public float getScrollPercentage()
	{
		float scrollHeight = this.baseBar.getDimensions().getHeight() - this.grabbableBar.getDimensions().getHeight();

		float scrollPosition = this.grabbableBar.getDimensions().getY() - this.baseBar.getDimensions().getY();

		return scrollPosition / scrollHeight;
	}
	
	public void setContentDimensions(Dimensions2D contentDimensions)
	{
		this.contentDimensions = contentDimensions;
	}
	
	public Dimensions2D getContentDimensions()
	{
		return this.contentDimensions;
	}

	private void snapBarToProportions()
	{
		int bottomY = this.baseBar.getDimensions().getY() + this.baseBar.getDimensions().getHeight() - this.grabbableBar.getDimensions().getHeight();

		int topY = Math.max(this.baseBar.getDimensions().getY(), this.grabbableBar.getDimensions().getY());

		this.grabbableBar.getDimensions().setY(Math.min(bottomY, topY));
	}

	private void refreshProportions(InputProvider input)
	{
		if (this.getContentDimensions() != null)
		{
			//this.grabbableBar.getDimensions().setHeight(this.getContentDimensions().getHeight() / this.baseBar.getDimensions().getHeight());
		}
		
		if (this.grabbedBar)
		{
			this.grabbableBar.getDimensions().setY(input.getMouseY() + this.grabbedMouseYOffset);
		}

		this.snapBarToProportions();
	}
}
