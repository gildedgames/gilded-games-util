package com.gildedgames.util.core.gui;

import com.gildedgames.util.core.gui.util.UIFactory;
import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.TickInfo;
import com.gildedgames.util.ui.data.UIElementContainer;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.ButtonList;
import com.gildedgames.util.ui.util.decorators.ScrollableUI;
import com.gildedgames.util.ui.util.factory.TestButtonFactory;
import com.gildedgames.util.ui.util.transform.UIViewPositionerButton;

public class TestUI extends AbstractUI
{

	public TestUI(AbstractUI parent)
	{
		super(parent, Dim2D.buildCommit());
	}

	@Override
	public void onInit(UIElementContainer container, InputProvider input)
	{
		super.onInit(container, input);
		
		Dim2D dim = Dim2D.build().area(80, 200).commit();

		ButtonList buttonList = new ButtonList(new UIViewPositionerButton(), new TestButtonFactory());

		ScrollableUI scrollable = new ScrollableUI(dim, buttonList, UIFactory.createScrollBar());

		scrollable.modDim().resetPos().commit();

		container.setElement("scrollable", scrollable);

		/*Dimensions2D dim1 = new Dimensions2D().setArea(50, 50);
		Dimensions2D dim2 = new Dimensions2D().setPos(new Position2D(20, 30));
		Dimensions2D dim3 = new Dimensions2D().setPos(new Position2D(0, -10));
		
		this.dim2Holder = new Dimensions2DModifier(dim2);
		this.dim3Holder = new Dimensions2DModifier(dim3);
		
		dim1.addModifier(this.dim2Holder);
		dim1.addModifier(this.dim3Holder);
		
		this.rectangle = new RectangleElement(dim1, new DrawingData());
		
		container.add(this.rectangle);*/
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
	}
	
	@Override
	public void tick(InputProvider input, TickInfo tickInfo)
	{
		super.tick(input, tickInfo);
	}

}
