package com.gildedgames.util.ui;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.IKeyboardListener;
import com.gildedgames.util.ui.listeners.IMouseListener;
import com.gildedgames.util.ui.listeners.MouseButton;
import com.gildedgames.util.ui.util.UIElementWrapper;


public abstract class UIFrame implements UIView, UIElementHolder, IKeyboardListener, IMouseListener
{
	
	private final UIElementWrapper elementWrapper;
	
	protected boolean visible = true;

	protected boolean enabled = true;
	
	protected Dimensions2D focusArea;
	
	public UIFrame(Dimensions2D focusArea, Dimensions2D screenDimensions)
	{
		this.elementWrapper = new UIElementWrapper(screenDimensions);
		
		this.focusArea = focusArea;
	}
	
	@Override
	public void init(UIElementHolder elementHolder, Dimensions2D screenDimensions)
	{
		this.elementWrapper.init(elementHolder, screenDimensions);
	}
	
	@Override
	public void draw(IGraphics graphics)
	{
		this.elementWrapper.draw(graphics);
	}
	
	@Override
	public final void add(UIElement element)
	{
		this.elementWrapper.add(element);
	}

	@Override
	public final void remove(UIElement element)
	{
		this.elementWrapper.remove(element);
	}
	
	@Override
	public boolean onKeyState(char charTyped, int keyTyped, ButtonState state)
	{
		return this.elementWrapper.onKeyState(charTyped, keyTyped, state);
	}
	
	@Override
	public void onMouseState(int mouseX, int mouseY, MouseButton button, ButtonState state)
	{
		this.elementWrapper.onMouseState(mouseX, mouseY, button, state);
	}

	@Override
	public void onMouseScroll(int mouseX, int mouseY, int scrollDifference)
	{
		this.elementWrapper.onMouseScroll(mouseX, mouseY, scrollDifference);
	}
	
	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	@Override
	public Dimensions2D getFocusArea()
	{
		return this.focusArea;
	}

	@Override
	public void setFocusArea(Dimensions2D focusArea)
	{
		this.focusArea = focusArea;
	}
	
}
