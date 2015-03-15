package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.event.view.UIEventViewMouse;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseEventPool;

public class UIScrollBar extends UIFrame
{

	/**
	 * Rectangle describing the dimensions of the frame this
	 * scrollbar is mounted to 
	 */
	protected Dimensions2D scrollableFrame;

	protected UIBase topArrowButton, bottomArrowButton;

	/**
	 * The two textures used in the scrollbar. They are
	 * repeated to make longer bars.
	 */
	protected UITexture baseTexture, barTexture;
	
	protected UIRepeatable repeatedBase, repeatedBar;

	/**
	 * Dimensions of the part of the scroll bar that
	 * you can grab and move around.
	 */
	protected Dimensions2D bar;

	protected Dimensions2D contentDimensions;
	
	/**
	 * True if the bar is grabbed.
	 */
	private boolean grabbedBar;

	/**
	 * The whole scroll bar
	 */
	private Dimensions2D base;

	protected float scrollSpeed = 0.075F, grabbedMouseYOffset;

	public UIScrollBar(Dimensions2D barDim, Dimensions2D scrollableFrame, UIBase topArrowButton, UIBase bottomArrowButton, UITexture baseTexture, UITexture barTexture)
	{
		super(null, barDim);

		this.scrollableFrame = scrollableFrame;

		this.topArrowButton = topArrowButton;
		this.bottomArrowButton = bottomArrowButton;
		
		this.baseTexture = baseTexture;
		this.barTexture = barTexture;

		this.bar = new Dimensions2D().setPos(this.getDimensions().getPosition()).setWidth(this.barTexture.getDimensions().getWidth());
		
		float maxWidth = Math.max(Math.max(this.topArrowButton.getDimensions().getWidth(), this.bottomArrowButton.getDimensions().getWidth()), this.baseTexture.getDimensions().getWidth());

		this.getDimensions().setWidth(maxWidth);
	}
	
	public void setScrollableFrame(Dimensions2D scrollableFrame)
	{
		this.scrollableFrame = scrollableFrame;
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
	public void init(UIElementContainer container, InputProvider input)
	{
		super.init(container, input);

		this.getDimensions().setPos(this.scrollableFrame.getPosition());

		this.topArrowButton.getDimensions().setPos(this.getDimensions().getPosition());
		this.bottomArrowButton.getDimensions().setY(this.getDimensions().getY() + this.getDimensions().getHeight() - this.bottomArrowButton.getDimensions().getHeight());

		this.topArrowButton.getDimensions().setCentering(this.getDimensions().isCenteredX(), this.getDimensions().isCenteredY());
		this.bottomArrowButton.getDimensions().setCentering(this.getDimensions().isCenteredX(), this.getDimensions().isCenteredY());
		
		this.topArrowButton.add(new UIEventViewMouse(this)
		{

			@Override
			public void onMouseEvent(InputProvider input, MouseEventPool pool)
			{
				UIScrollBar scrollBar = new ObjectFilter().getType(this.view, UIScrollBar.class);

				if (pool.has(MouseButton.LEFT) && input.isHovered(scrollBar.topArrowButton.getDimensions()))
				{
					scrollBar.bar.addY(-5);
					scrollBar.snapBarToProportions();
				}
			}
		
		});
		
		this.bottomArrowButton.add(new UIEventViewMouse(this)
		{

			@Override
			public void onMouseEvent(InputProvider input, MouseEventPool pool)
			{
				UIScrollBar scrollBar = new ObjectFilter().getType(this.view, UIScrollBar.class);
				
				if (input.isHovered(scrollBar.bottomArrowButton.getDimensions()))
				{
					scrollBar.bar.addY(5);
					scrollBar.snapBarToProportions();
				}
			}
		
		});

		container.add(this.topArrowButton);
		container.add(this.bottomArrowButton);

		float heightOffset = this.bottomArrowButton.getDimensions().getHeight() + this.topArrowButton.getDimensions().getHeight();

		this.bar.addY(this.topArrowButton.getDimensions().getHeight());
		this.base = this.getDimensions().clone().addHeight(-heightOffset).addY(this.topArrowButton.getDimensions().getHeight() + 0.5F);

		this.repeatedBase = new UIRepeatable(this.base, this.baseTexture);
		this.repeatedBar = new UIRepeatable(this.bar, this.barTexture);
		
		this.repeatedBase.getDimensions().addY(this.topArrowButton.getDimensions().getHeight());
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.bar) || input.isHovered(this.scrollableFrame))
		{
			this.bar.addY(-scrollDifference * this.scrollSpeed);
			this.snapBarToProportions();
		}
	}

	@Override
	public void onMouseEvent(InputProvider input, MouseEventPool pool)
	{
		super.onMouseEvent(input, pool);

		if (this.grabbedBar && !pool.has(ButtonState.DOWN))
		{
			this.grabbedBar = false;
		}

		if (pool.has(MouseButton.LEFT))
		{
			if (pool.has(ButtonState.PRESSED) && input.isHovered(this.getBase()))
			{
				this.grabbedBar = true;
				
				if (input.isHovered(this.bar))
				{
					this.grabbedMouseYOffset = this.bar.getY() - input.getMouseY();
				}
				else
				{
					this.grabbedMouseYOffset = -this.bar.getHeight() / 2;
				}
			}
			else if (pool.has(ButtonState.RELEASED))
			{
				this.grabbedBar = false;
			}
		}
	}

	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		this.refreshProportions(input);

		this.repeatedBar.getDimensions().setY(this.bar.getY());
		
		this.repeatedBase.draw(graphics, input);
		this.repeatedBar.draw(graphics, input);

		super.draw(graphics, input);
	}
	
	/**
	 * @return A float value between 0.0F - 1.0F which represents the position of the grabbed bar on the scroll base.
	 */
	public float getScrollPercentage()
	{
		float scrollHeight = this.getBase().getHeight() - this.bar.getHeight();

		float scrollPosition = this.bar.getY() - this.getBase().getY();

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
		float bottomY = this.getBase().getY() + this.getBase().getHeight() - this.bar.getHeight();

		float topY = Math.max(this.getBase().getY(), this.bar.getY());
		
		float snappedY = Math.min(bottomY, topY);

		this.bar.setY(snappedY);
	}

	private void refreshProportions(InputProvider input)
	{
		this.bar.setHeight(this.getContentDimensions().getHeight() / this.getBase().getHeight());

		if (this.grabbedBar)
		{
			this.bar.setY(input.getMouseY() + this.grabbedMouseYOffset);
		}

		this.snapBarToProportions();
	}

	private Dimensions2D getBase()
	{
		return this.base;
	}

}
