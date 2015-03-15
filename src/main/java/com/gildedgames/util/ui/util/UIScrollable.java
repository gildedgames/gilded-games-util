package com.gildedgames.util.ui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import com.gildedgames.util.ui.UIElement;
import com.gildedgames.util.ui.UIElementContainer;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.input.InputProvider;

public class UIScrollable extends UIScissorable
{
	
	protected Minecraft mc = Minecraft.getMinecraft();

	protected Dimensions2D box;
	
	protected int totalHeight;
	
	protected UIScrollBar scrollBar;
	
	protected InputProvider shiftedInput;
	
	protected int shiftedWidth;
	
	public UIScrollable(Dimensions2D dim, UIElement element, UIScrollBar scrollBar)
	{
		super(dim, element);
		
		this.box = dim;
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void init(UIElementContainer container, InputProvider input)
	{
		this.shiftedWidth = (int) this.scrollBar.getDimensions().getWidth();
		
		this.shiftedInput = input.copyWithMouseXOffset(this.shiftedWidth);
		
		super.init(container, this.shiftedInput);
		
		container.add(this.scrollBar);
		
		this.box.setX(this.scrollBar.getDimensions().getX() + this.shiftedWidth);
		
		this.scrollBar.setContentDimensions(this.getDimensions());
	}
	
	@Override
	public void draw(IGraphics graphics, InputProvider input)
	{
		float scrollValue = -this.scrollBar.getScrollPercentage() * (this.getDimensions().getHeight() - this.box.getHeight());

		GlStateManager.pushMatrix();

		GlStateManager.translate(this.shiftedWidth, scrollValue, 0.0F);
		
		this.shiftedInput = this.shiftedInput.copyWithMouseXOffset(this.shiftedWidth);
		this.shiftedInput = this.shiftedInput.copyWithMouseYOffset((int)scrollValue);

		super.draw(graphics, this.shiftedInput);

		GlStateManager.popMatrix();
	}

}