package com.gildedgames.util.ui;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DimensionsHolder;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;
import com.gildedgames.util.ui.util.OriginDecorator;

public class UIFrame implements UIView, KeyboardListener, MouseListener
{

	private UIContainer parentNode = new UIContainer();
	
	private UIElement framedElement;
	
	public UIFrame(UIElement framedElement)
	{
		this.framedElement = framedElement;
		
		this.parentNode.add(this.framedElement);
	}
	
	public UIElement getFramedElement()
	{
		return this.framedElement;
	}

	public void onInit(InputProvider input)
	{
		this.parentNode.clear();
		this.parentNode.add(this.framedElement);
		
		this.onInit(this.parentNode, input);
	}
	
	public void onResolutionChange(InputProvider input)
	{
		this.onInit(input);
	}
	
	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (UIElement element : ObjectFilter.getTypesFrom(container.values(), UIElement.class))
		{
			UIContainer internal = container.getInternal(element);
			
			element.onInit(internal, input);
			
			DimensionsHolder origin = ObjectFilter.getType(element, DimensionsHolder.class);
			
			for (UIElement internalElement : internal)
			{
				DimensionsHolder child = ObjectFilter.getType(internalElement, DimensionsHolder.class);
				
				if (child != null && origin != null)
				{
					if (child.getDimensions().getOrigin() == null)
					{
						child.getDimensions().setOrigin(origin);
					}
					else
					{
						OriginDecorator decoratedOrigin = new OriginDecorator(origin, child.getDimensions().getOrigin().getDimensions());
						
						child.getDimensions().setOrigin(decoratedOrigin);
					}
				}
			}
			
			UIBasic basic = ObjectFilter.getType(element, UIBasic.class);
			
			if (basic != null)
			{
				this.onInit(basic.getListeners(), input);
			}
			
			this.onInit(internal, input);
		}
	}

	@Override
	public void onResolutionChange(UIContainer container, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (UIElement element : ObjectFilter.getTypesFrom(container.values(), UIElement.class))
		{
			UIContainer internal = container.getInternal(element);
			
			element.onResolutionChange(internal, input);
			
			DimensionsHolder origin = ObjectFilter.getType(element, DimensionsHolder.class);
			
			if (origin != null)
			{
				for (UIElement internalElement : internal)
				{
					DimensionsHolder child = ObjectFilter.getType(internalElement, DimensionsHolder.class);
					
					if (child != null && origin != null)
					{
						if (child.getDimensions().getOrigin() == null)
						{
							child.getDimensions().setOrigin(origin);
						}
						else
						{
							OriginDecorator decoratedOrigin = new OriginDecorator(origin, child.getDimensions().getOrigin().getDimensions());
							
							child.getDimensions().setOrigin(decoratedOrigin);
						}
					}
				}
			}
			
			UIBasic basic = ObjectFilter.getType(element, UIBasic.class);
			
			if (basic != null)
			{
				this.onResolutionChange(basic.getListeners(), input);
			}

			this.onResolutionChange(internal, input);
		}
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		this.onMouseInput(this.parentNode, input, pool);
	}
	
	private void onMouseInput(UIContainer container, InputProvider input, MouseInputPool pool)
	{
		if (container == null)
		{
			return;
		}
		
		for (MouseListener element : ObjectFilter.getTypesFrom(container.values(), MouseListener.class))
		{
			UIContainer internal = container.getInternal(element);
			
			if (element.isEnabled())
			{
				element.onMouseInput(input, pool);
				
				UIBasic basic = ObjectFilter.getType(element, UIBasic.class);
				
				if (basic != null)
				{
					this.onMouseInput(basic.getListeners(), input, pool);
				}
				
				this.onMouseInput(internal, input, pool);
			}
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		this.onMouseScroll(this.parentNode, input, scrollDifference);
	}
	
	private void onMouseScroll(UIContainer container, InputProvider input, int scrollDifference)
	{
		if (container == null)
		{
			return;
		}
		
		for (MouseListener element : ObjectFilter.getTypesFrom(container.values(), MouseListener.class))
		{
			UIContainer internal = container.getInternal(element);
			
			if (element.isEnabled())
			{
				element.onMouseScroll(input, scrollDifference);
				
				UIBasic basic = ObjectFilter.getType(element, UIBasic.class);
				
				if (basic != null)
				{
					this.onMouseScroll(basic.getListeners(), input, scrollDifference);
				}
				
				this.onMouseScroll(internal, input, scrollDifference);
			}
		}
	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return this.onKeyboardInput(this.parentNode, pool);
	}
	
	private boolean onKeyboardInput(UIContainer container, KeyboardInputPool pool)
	{
		if (container == null)
		{
			return false;
		}
		
		boolean success = false;
		
		for (KeyboardListener element : ObjectFilter.getTypesFrom(container.values(), KeyboardListener.class))
		{
			UIContainer internal = container.getInternal(element);
			
			if (element.isEnabled())
			{
				UIBasic basic = ObjectFilter.getType(element, UIBasic.class);

				success = element.onKeyboardInput(pool)
						|| this.onKeyboardInput(internal, pool)
						|| (basic != null && this.onKeyboardInput(basic.getListeners(), pool))
						|| success;
			}
		}
		
		return success;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.draw(this.parentNode, graphics, input);
	}
	
	private void draw(UIContainer container, Graphics2D graphics, InputProvider input)
	{
		if (container == null)
		{
			return;
		}
		
		for (UIView element : ObjectFilter.getTypesFrom(container.values(), UIView.class))
		{
			UIContainer internal = container.getInternal(element);
			
			if (element.isVisible())
			{
				element.draw(graphics, input);
				
				UIBasic basic = ObjectFilter.getType(element, UIBasic.class);
				
				if (basic != null)
				{
					this.draw(basic.getListeners(), graphics, input);
				}
				
				this.draw(internal, graphics, input);
			}
		}
	}
	
	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		
	}

	@Override
	public boolean isVisible()
	{
		return true;
	}

	@Override
	public void setVisible(boolean visible)
	{
		
	}

	@Override
	public Dimensions2D getDimensions()
	{
		return new Dimensions2D();
	}

	public boolean isFocused()
	{
		return true;
	}

	@Override
	public void setFocused(boolean focused)
	{
		
	}

	@Override
	public boolean query(Object... input)
	{
		return false;
	}

}