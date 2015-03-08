package com.gildedgames.util.ui.util;

import java.util.List;

import com.gildedgames.util.ui.UIBase;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.UIFrame;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIScrollBar extends UIFrame
{
	
	protected Dimensions2D scrollableFrame;
	
	protected UIBase topArrowButton, bottomArrowButton;
	
	protected UITexture repeatedBase, repeatedBar;
	
	protected Dimensions2D bar;
	
	private boolean selectedBar;

	private Dimensions2D base;
	
	protected float scrollSpeed = 0.075F;

	public UIScrollBar(Dimensions2D barDim, Dimensions2D scrollableFrame, UIBase topArrowButton, UIBase bottomArrowButton, UITexture repeatedBase, UITexture repeatedBar)
	{
		super(null, barDim);
		
		this.scrollableFrame = scrollableFrame;
		
		this.topArrowButton = topArrowButton;
		this.bottomArrowButton = bottomArrowButton;
		
		this.repeatedBase = repeatedBase;
		this.repeatedBar = repeatedBar;
		
		this.bar = new Dimensions2D().withPos(this.getDimensions().getPosition()).withWidth(this.repeatedBar.getDimensions().getWidth());
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
		this.bottomArrowButton.getDimensions().setY(this.getDimensions().getHeight() + this.getDimensions().getY());
		
		container.add(this.topArrowButton);
		container.add(this.bottomArrowButton);
		
		float heightOffset = this.bottomArrowButton.getDimensions().getHeight() + this.topArrowButton.getDimensions().getHeight();

		this.bar.withAddedY(this.topArrowButton.getDimensions().getHeight());
		this.base = this.getDimensions().withAddedHeight(-heightOffset).withAddedY(this.topArrowButton.getDimensions().getHeight());
		
		this.repeatedBase.getDimensions().withAddedY(this.topArrowButton.getDimensions().getHeight());
	}
	
	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		super.onMouseScroll(input, scrollDifference);

		if (input.isHovered(this.bar) || input.isHovered(this.scrollableFrame))
		{
			this.bar.addY(-scrollDifference * this.scrollSpeed);
		}
	}
	
	@Override
	public void onMouseState(InputProvider input, List<MouseButton> buttons, List<ButtonState> states)
	{
		super.onMouseState(input, buttons, states);

		if (this.selectedBar && !states.contains(ButtonState.DOWN))
		{
			this.selectedBar = false;
		}
		
		if (buttons.contains(MouseButton.LEFT))
		{
			if (states.contains(ButtonState.PRESS) && input.isHovered(this.getDimensions()))
			{
				this.selectedBar = true;
			}
			else if (states.contains(ButtonState.RELEASED))
			{
				this.selectedBar = false;
			}
		}
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		this.refreshProportions(input);

		float baseHeight = this.repeatedBase.getDimensions().getHeight();
		int baseCount = (int) (this.getBase().getHeight() / baseHeight);
		
		float barHeight = this.repeatedBar.getDimensions().getHeight();
		int barCount = (int) (this.bar.getHeight() / barHeight);
		
		Dimensions2D oldBaseDim = this.repeatedBase.getDimensions().clone();
		Dimensions2D oldBarDim = this.repeatedBar.getDimensions().clone();
		
		this.repeatedBar.getDimensions().setY(this.bar.getY());

		for (int drawBaseCount = 0; drawBaseCount < baseCount; drawBaseCount++)
		{
			this.repeatedBase.getDimensions().addY(baseHeight);
			
			this.repeatedBase.draw(graphics, input);
		}

		for (int drawBarCount = 0; drawBarCount < barCount; drawBarCount++)
		{
			this.repeatedBar.getDimensions().addY(barHeight);
			
			this.repeatedBar.draw(graphics, input);
		}
		
		this.repeatedBase.setDimensions(oldBaseDim);
		this.repeatedBar.setDimensions(oldBarDim);
		
		super.draw(graphics, input);
	}
	
	private void snapBarToProportions()
	{
		float snappedY = Math.min(this.getBase().getY() + this.getBase().getHeight(), Math.max(this.getBase().getY(), this.bar.getY()));
		
		this.bar.setY(snappedY);
	}
	
	private void refreshProportions(InputProvider input)
	{
		this.bar.setHeight(this.getBase().getHeight() / (this.scrollableFrame.getHeight() / this.getBase().getHeight()));
		
		if (this.selectedBar)
		{
			this.bar.setY(input.getMouseY() - this.bar.getHeight());
		}
		
		this.snapBarToProportions();
	}
	
	private Dimensions2D getBase()
	{
		return this.base;
	}

}