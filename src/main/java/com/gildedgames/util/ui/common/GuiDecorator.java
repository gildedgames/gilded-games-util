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
import com.gildedgames.util.ui.util.GuiViewerHelper;


public abstract class GuiDecorator<T extends Ui> extends GuiFrame
{

	private T element;
	
	public GuiDecorator(T element)
	{
		super(Dim2D.build().flush());
		
		this.element = element;
	}
	
	public T getDecoratedElement()
	{
		return this.element;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		Gui view = ObjectFilter.getType(this.element, Gui.class);
		
		if (view != null)
		{
			view.draw(graphics, input);
		}
	}

	@Override
	public boolean isVisible()
	{
		Gui view = ObjectFilter.getType(this.element, Gui.class);
		
		if (view != null)
		{
			return view.isVisible();
		}
		
		return false;
	}

	@Override
	public void setVisible(boolean visible)
	{
		Gui view = ObjectFilter.getType(this.element, Gui.class);
		
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
	public void tick(TickInfo tickInfo, InputProvider input)
	{
		this.element.tick(tickInfo, input);
	}
	
	@Override
	public final void init(InputProvider input)
	{
		this.initContent(input);
		
		GuiViewerHelper.processInitPre(this, input, this.content(), this.listeners());
	}
	
	@Override
	public final void initContent(InputProvider input)
	{
		this.preInitContent(input);
		
		this.element.initContent(input);
		
		this.postInitContent(input);
	}
	
	@Override
	public final void onClose(InputProvider input)
	{
		this.preClose(input);
		
		this.element.onClose(input);
		
		this.postClose(input);
	}
	
	protected void preClose(InputProvider input)
	{
		
	}
	
	protected void postClose(InputProvider input)
	{
		
	}
	
	protected abstract void preInitContent(InputProvider input);
	
	protected abstract void postInitContent(InputProvider input);

	@Override
	public void onResolutionChange(InputProvider input)
	{
		this.init(input);
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
	public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
	{
		KeyboardListener listener = ObjectFilter.getType(this.element, KeyboardListener.class);
		
		if (listener != null)
		{
			return listener.onKeyboardInput(pool, input);
		}
		
		return false;
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		MouseListener listener = ObjectFilter.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseInput(pool, input);
		}
	}

	@Override
	public void onMouseScroll(int scrollDifference, InputProvider input)
	{
		MouseListener listener = ObjectFilter.getType(this.element, MouseListener.class);
		
		if (listener != null)
		{
			listener.onMouseScroll(scrollDifference, input);
		}
	}

	@Override
	public boolean isFocused()
	{
		Gui view = ObjectFilter.getType(this.element, Gui.class);
		
		if (view != null)
		{
			return view.isFocused();
		}
		
		return false;
	}

	@Override
	public void setFocused(boolean focused)
	{
		Gui view = ObjectFilter.getType(this.element, Gui.class);
		
		if (view != null)
		{
			view.setFocused(focused);
		}
	}
	
	@Override
	public boolean query(Object... input)
	{
		Gui view = ObjectFilter.getType(this.element, Gui.class);
		
		if (view != null)
		{
			return view.query(input);
		}
		
		return false;
	}
	
	@Override
	public UIContainer seekContent()
	{
		return this.element.seekContent();
	}
	
	@Override
	public UIContainerMutable content()
	{
		GuiFrame frame = ObjectFilter.getType(this.element, GuiFrame.class);
		
		if (frame != null)
		{
			return frame.content();
		}
		
		return null;
	}

	@Override
	public UIContainerMutable listeners()
	{
		GuiFrame frame = ObjectFilter.getType(this.element, GuiFrame.class);
		
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
