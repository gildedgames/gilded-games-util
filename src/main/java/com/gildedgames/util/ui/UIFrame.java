package com.gildedgames.util.ui;

import java.util.Arrays;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.listeners.KeyboardListener;
import com.gildedgames.util.ui.listeners.MouseListener;
import com.gildedgames.util.ui.util.ObjectFilter;

public abstract class UIFrame implements UIView, UIHearable, KeyboardListener, MouseListener
{
	
	protected final static ObjectFilter FILTER = new ObjectFilter();

	protected boolean visible = true, enabled = true, focused = false;

	protected UIContainer listeners = new UIContainer(), internalContainer = new UIContainer();
	
	protected UIFrame previousFrame;
	
	protected Dimensions2D dimensions;
	
	public UIFrame(Dimensions2D dimensions)
	{
		this(null, dimensions);
	}

	public UIFrame(UIFrame previousFrame, Dimensions2D dimensions)
	{
		this.previousFrame = previousFrame;
		this.dimensions = dimensions;
	}
	
	public void onInit(InputProvider input)
	{
		for (UIContainer container : Arrays.asList(this.getInternalContainer(), this.getListeners()))
		{
			this.onInit(container, input);
		}
	}
	
	public void onResolutionChange(InputProvider input)
	{
		for (UIContainer container : Arrays.asList(this.getInternalContainer(), this.getListeners()))
		{
			this.onResolutionChange(container, input);
		}
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		this.crawlContainers(new DrawCrawl(graphics, input), this.getInternalContainer(), this.getListeners());
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
	public boolean onKeyboardInput(KeyboardInputPool pool)
	{
		return this.crawlContainers(new KeyboardInputCrawl(pool), this.getInternalContainer(), this.getListeners());
	}

	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		this.crawlContainers(new MouseInputCrawl(input, pool), this.getInternalContainer(), this.getListeners());
	}

	@Override
	public void onMouseScroll(InputProvider input, int scrollDifference)
	{
		this.crawlContainers(new MouseScrollCrawl(input, scrollDifference), this.getInternalContainer(), this.getListeners());
	}
	
	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		this.crawlContainers(new OnInitCrawl(input, false), container);
	}
	
	@Override
	public void onResolutionChange(UIContainer container, InputProvider input)
	{
		this.crawlContainers(new OnInitCrawl(input, true), container);
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
	public Dimensions2D getDimensions()
	{
		return this.dimensions;
	}

	@Override
	public void setDimensions(Dimensions2D dimensions)
	{
		this.dimensions = dimensions;
	}

	@Override
	public boolean isFocused()
	{
		return this.focused;
	}

	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	@Override
	public boolean query(Object... input)
	{
		/*List<UIContainer> containers = new ArrayList<UIContainer>();
		
		containers.add(this.getListeners());
		containers.add(this.getInternalContainer());
		
		for (UIContainer container : containers)
		{
			for (UIView element : FILTER.getTypesFrom(container.getElements(), UIView.class))
			{
				if (element == null)
				{
					continue;
				}

				if (element.query(input))
				{
					return true;
				}
			}
		}*/

		return false;
	}

	@Override
	public UIContainer getListeners()
	{
		return this.listeners;
	}

	public UIFrame getPreviousFrame()
	{
		return this.previousFrame;
	}
	
	private UIContainer getInternalContainer()
	{
		return this.internalContainer;
	}

	public Dimensions2D getWrapperDimensions()
	{
		return this.dimensions;
		
		/*List<Dimensions2D> areas = new ArrayList<Dimensions2D>();
		
		List<UIContainer> containers = new ArrayList<UIContainer>(this.internalContainers.values());
		
		containers.add(this.getListeners());
		containers.add(this.getInternalContainer());

		for (UIContainer container : containers)
		{
			for (UIElement element : container.getElements())
			{
				if (element instanceof UIView)
				{
					UIView view = (UIView) element;

					areas.add(view.getDimensions());
				}
			}
		}

		return Dimensions2D.combine(areas);*/
	}
	
	private boolean crawlContainers(ContainerCrawl action, UIContainer... containers)
	{
		boolean crawlSuccess = false;
		
		for (UIContainer container : containers)
		{
			if (action.crawl(container) && !crawlSuccess)
			{
				crawlSuccess = true;
			}
			
			UIContainer[] internalContainers = container.getChildren().toArray(new UIContainer[container.getChildren().size()]);

			boolean crawlSubSuccess = this.crawlContainers(action, internalContainers);
			
			if (crawlSubSuccess && !crawlSuccess)
			{
				crawlSuccess = true;
			}
		}
		
		return crawlSuccess;
	}
	
	private static abstract class ContainerCrawl
	{
		
		protected abstract boolean crawl(UIContainer container);
		
	}
	
	private static class DrawCrawl extends ContainerCrawl
	{
		
		private final Graphics2D graphics;
		
		private final InputProvider input;
		
		public DrawCrawl(Graphics2D graphics, InputProvider input)
		{
			this.graphics = graphics;
			this.input = input;
		}

		@Override
		protected boolean crawl(UIContainer container)
		{
			for (UIView element : FILTER.getTypesFrom(container.getElements(), UIView.class))
			{
				if (element != null && element.isVisible())
				{
					element.draw(this.graphics, this.input);
				}
			}
			
			return false;
		}
	
	}
	
	private static class KeyboardInputCrawl extends ContainerCrawl
	{
		
		private final KeyboardInputPool pool;

		public KeyboardInputCrawl(KeyboardInputPool pool)
		{
			this.pool = pool;
		}

		@Override
		protected boolean crawl(UIContainer container)
		{
			for (KeyboardListener element : FILTER.getTypesFrom(container.getElements(), KeyboardListener.class))
			{
				if (element != null && element.isEnabled() && element.onKeyboardInput(this.pool))
				{
					return true;
				}
			}
			
			return false;
		}
	
	}
	
	private static class MouseInputCrawl extends ContainerCrawl
	{
		
		private final InputProvider input;
		
		private final MouseInputPool pool;

		public MouseInputCrawl(InputProvider input, MouseInputPool pool)
		{
			this.input = input;
			this.pool = pool;
		}

		@Override
		protected boolean crawl(UIContainer container)
		{
			for (MouseListener element : FILTER.getTypesFrom(container.getElements(), MouseListener.class))
			{
				if (element != null && element.isEnabled())
				{
					element.onMouseInput(this.input, this.pool);
				}
			}
			
			return false;
		}
	
	}
	
	private static class MouseScrollCrawl extends ContainerCrawl
	{
		
		private final InputProvider input;
		
		private final int scrollDifference;

		public MouseScrollCrawl(InputProvider input, int scrollDifference)
		{
			this.input = input;
			this.scrollDifference = scrollDifference;
		}

		@Override
		protected boolean crawl(UIContainer container)
		{
			for (MouseListener element : FILTER.getTypesFrom(container.getElements(), MouseListener.class))
			{
				if (element != null && element.isEnabled())
				{
					element.onMouseScroll(this.input, this.scrollDifference);
				}
			}
			
			return false;
		}
	
	}
	
	private static class OnInitCrawl extends ContainerCrawl
	{

		private final InputProvider input;
		
		private final boolean resolutionChange;

		public OnInitCrawl(InputProvider input, boolean resolutionChange)
		{
			this.input = input;
			this.resolutionChange = resolutionChange;
		}

		@Override
		protected boolean crawl(UIContainer container)
		{
			for (UIElement element : container)
			{
				if (element == null)
				{
					continue;
				}
				
				if (this.resolutionChange)
				{
					element.onResolutionChange(container.getInternal(element), input);
				}
				else
				{
					element.onInit(container.getInternal(element), input);
				}
				
				UIView view;
				
				if ((view = FILTER.getType(element, UIView.class)) != null)
				{
					for (UIView child : FILTER.getTypesFrom(container.getInternal(element).getElements(), UIView.class))
					{
						if (child != null)
						{
							child.getDimensions().setOrigin(view.getDimensions().getPos());
						}
					}
				}
			}
			
			return false;
		}
	
	}

}