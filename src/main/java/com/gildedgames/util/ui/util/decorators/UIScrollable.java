package com.gildedgames.util.ui.util.decorators;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIContainer;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.frames.UIScrollBar;

public class UIScrollable extends UIDecorator<UIView>
{
	
	protected Minecraft mc = Minecraft.getMinecraft();

	protected Dimensions2D scrollArea;

	protected UIScrollBar scrollBar;

	public UIScrollable(Dimensions2D scrollArea, UIView view, UIScrollBar scrollBar)
	{
		super(new UIScissorable(scrollArea, view));
		
		this.scrollArea = scrollArea;
		this.scrollBar = scrollBar;
	}
	
	@Override
	public void onInit(UIContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		container.add(this.scrollBar);
		
		this.scrollBar.setContentDimensions(this.getDimensions());
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		float scrollValue = -this.scrollBar.getScrollPercentage() * (this.getDimensions().getHeight() - this.scrollArea.getHeight());

		Position2D shiftedPos = this.scrollArea.getPos().withAdded(this.scrollBar.getDimensions().getWidth(), scrollValue);

		this.getDimensions().setPos(shiftedPos);
		
		super.draw(graphics, input);
	}

}