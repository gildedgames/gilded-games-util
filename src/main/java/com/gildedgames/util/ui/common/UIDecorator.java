package com.gildedgames.util.ui.common;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Dim2D.Dim2DBuilder;
import com.gildedgames.util.ui.data.Dim2D.Dim2DModifier;
import com.gildedgames.util.ui.data.Dim2DHolder;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;


public abstract class UIDecorator<T extends UIElement> extends UIFrame
{

	private T element;
	
	public UIDecorator(T element)
	{
		super(Dim2D.build().compile());
		
		this.element = element;
	}
	
	public T getDecoratedElement()
	{
		return this.element;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.draw(graphics, input);
		}
	}

	@Override
	public boolean isVisible()
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.isVisible();
		}
		
		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setVisible(visible);
		}
	}

	@Override
	public Dim2D getDim()
	{
		Dim2DHolder holder = ObjectFilter.getType(this.element, Dim2DHolder.class);
		
		if (holder != null)
		{
			return holder.getDim();
		}
		
		return null;
	}
	
	@Override
	public void setDim(Dim2D dim)
	{
		Dim2DHolder holder = ObjectFilter.getType(this.element, Dim2DHolder.class);
		
		if (holder != null)
		{
			holder.setDim(dim);
		}
	}
	
	@Override
	public Dim2DModifier modDim()
	{
		Dim2DHolder holder = ObjectFilter.getType(this.element, Dim2DHolder.class);
		
		if (holder != null)
		{
			return holder.modDim();
		}
		
		return null;
	}
	
	@Override
	public Dim2DBuilder copyDim()
	{
		Dim2DHolder holder = ObjectFilter.getType(this.element, Dim2DHolder.class);
		
		if (holder != null)
		{
			return holder.copyDim();
		}
		
		return null;
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		this.element.tick(input, tickInfo);
	}
	
	@Override
	public void init(InputProvider input)
	{
		this.element.init(input);
	}

	@Override
	public void onResolutionChange(InputProvider input)
	{
		this.element.onResolutionChange(input);
	}

	@Override
	public boolean isEnabled()
	{
		return this.element.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.element.setEnabled(enabled);
	}

	@Override
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		KeyboardListener listener = ObjectFilter.getType(this.element, KeyboardListener.class);
		
		if (listener != null)
		{
			return listener.onKeyboardInput(pool);
		}
		
		return false;
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		MouseListener listener = ObjectFilter.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseInput(input, pool);
		}
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		MouseListener listener = ObjectFilter.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseScroll(input, scrollDifference);
		}
	}

	@Override
	public boolean isFocused()
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.isFocused();
		}
		
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			view.setFocused(focused);
		}
	}
	
	@Override
	public boolean query(Object... input)
	{
		UIView view = ObjectFilter.getType(this.element, UIView.class);
		
		if (view != null)
		{
			return view.query(input);
		}
		
		return false;
	}
	
	@Override
	public UIContainer seekContent()
	{
		return this.assembleAllContent();
	}
	
	public UIContainer seekDecoratorContent()
	{
		return super.seekContent();
	}
	
	public abstract UIContainer assembleAllContent();

	@Override
	public UIContainerMutable listeners()
	{
		UIFrame frame = ObjectFilter.getType(this.element, UIFrame.class);
		
		if (frame != null)
		{
			return frame.listeners();
		}
		
		return null;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		this.element.write(output);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.element.read(input);
	}

}
