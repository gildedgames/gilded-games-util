package com.gildedgames.util.ui.util;

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
	protected UITexture baseTexture, barTexture;
	
	protected UIRepeatable base, bar;

	protected Dimensions2D contentDimensions;
	
	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	protected float scrollSpeed = 0.075F;

	protected int grabbedMouseYOffset;

	public UIScrollBar(Dimensions2D barDim, Dimensions2D scrollingArea, UIBasic topArrowButton, UIBasic bottomArrowButton, UITexture baseTexture, UITexture barTexture)
	{
		super(null, barDim);

		this.scrollingArea = scrollingArea;

		this.topArrowButton = topArrowButton;
		this.bottomArrowButton = bottomArrowButton;
		
		this.baseTexture = baseTexture;
		this.barTexture = barTexture;

		int maxWidth = Math.max(Math.max(this.topArrowButton.getDimensions().getWidth(), this.bottomArrowButton.getDimensions().getWidth()), this.baseTexture.getDimensions().getWidth());

		this.getDimensions().setWidth(maxWidth);
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

		this.bottomArrowButton.getDimensions().setY(this.getDimensions().getHeight() - this.bottomArrowButton.getDimensions().getHeight());

		System.out.println(this.bottomArrowButton.getDimensions().getPos());
		
		this.topArrowButton.getDimensions().setCentering(this.getDimensions().isCenteredX(), this.getDimensions().isCenteredY());
		this.bottomArrowButton.getDimensions().setCentering(this.getDimensions().isCenteredX(), this.getDimensions().isCenteredY());
		
		this.topArrowButton.getListeners().add(new MouseEventView(this)
		{

			@Override
			public void onMouseInput(InputProvider input, MouseInputPool pool)
			{
				UIScrollBar scrollBar = new ObjectFilter().getType(this.view, UIScrollBar.class);

				if (pool.has(MouseButton.LEFT) && input.isHovered(scrollBar.topArrowButton.getDimensions()))
				{
					scrollBar.bar.getDimensions().addY(-5);
					scrollBar.snapBarToProportions();
				}
			}
		
		});
		
		this.bottomArrowButton.getListeners().add(new MouseEventView(this)
		{

			@Override
			public void onMouseInput(InputProvider input, MouseInputPool pool)
			{
				UIScrollBar scrollBar = new ObjectFilter().getType(this.view, UIScrollBar.class);
				
				if (input.isHovered(scrollBar.bottomArrowButton.getDimensions()))
				{
					scrollBar.bar.getDimensions().addY(5);
					scrollBar.snapBarToProportions();
				}
			}
		
		});

		container.add(this.topArrowButton);
		container.add(this.bottomArrowButton);

		int heightOffset = this.bottomArrowButton.getDimensions().getHeight() + this.topArrowButton.getDimensions().getHeight();

		this.base = new UIRepeatable(new Dimensions2D(), this.baseTexture);
		this.bar = new UIRepeatable(new Dimensions2D(), this.barTexture);
		
		this.bar.getDimensions().addY(this.topArrowButton.getDimensions().getHeight());
		this.base.setDimensions(this.getDimensions().clone().addHeight(-heightOffset).addY(this.topArrowButton.getDimensions().getHeight() + 1));
		
		this.bar.setDimensions(new Dimensions2D().setPos(this.getDimensions().getPos()).setWidth(this.barTexture.getDimensions().getWidth()));
		
		this.base.getDimensions().addY(this.topArrowButton.getDimensions().getHeight());
		
		container.add(this.base);
		container.add(this.bar);
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.bar.getDimensions()) || input.isHovered(this.scrollingArea))
		{
			this.bar.getDimensions().addY((int)(-scrollDifference * this.scrollSpeed));
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
			if (pool.has(ButtonState.PRESSED) && input.isHovered(this.getBase()))
			{
				this.grabbedBar = true;
				
				if (input.isHovered(this.bar.getDimensions()))
				{
					this.grabbedMouseYOffset = this.bar.getDimensions().getY() - input.getMouseY();
				}
				else
				{
					this.grabbedMouseYOffset = -this.bar.getDimensions().getHeight() / 2;
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
		
		//
	}
	
	/**
	 * @return A float value between 0.0F - 1.0F which represents the position of the grabbed bar on the scroll base.
	 */
	public float getScrollPercentage()
	{
		float scrollHeight = this.getBase().getHeight() - this.bar.getDimensions().getHeight();

		float scrollPosition = this.bar.getDimensions().getY() - this.getBase().getY();

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
		int bottomY = this.getBase().getY() + this.getBase().getHeight() - this.bar.getDimensions().getHeight();

		int topY = Math.max(this.getBase().getY(), this.bar.getDimensions().getY());
		
		int snappedY = Math.min(bottomY, topY);

		this.bar.getDimensions().setY(snappedY);
	}

	private void refreshProportions(InputProvider input)
	{
		this.bar.getDimensions().setHeight(this.getContentDimensions().getHeight() / this.getBase().getHeight());

		if (this.grabbedBar)
		{
			this.bar.getDimensions().setY(input.getMouseY() + this.grabbedMouseYOffset);
		}

		this.snapBarToProportions();
	}

	private Dimensions2D getBase()
	{
		return this.base.getDimensions();
	}

}
