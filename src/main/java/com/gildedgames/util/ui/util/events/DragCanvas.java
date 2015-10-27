package com.gildedgames.util.ui.util.events;

import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.input.InputProvider;

public class DragCanvas extends GuiFrame
{

	public DragCanvas()
	{
		
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		
	}
	
	public <T extends GuiFrame> void setDraggedObject(T draggedObject)
	{
		this.content().set("draggedObject", draggedObject);
	}
	
	public <T extends GuiFrame> T getDraggedObject()
	{
		return (T) this.content().get("draggedObject", GuiFrame.class);
	}
	
	public void clearDraggedObject()
	{
		this.content().remove("draggedObject");
	}
	
	public static DragCanvas fetch()
	{
		if (UiCore.locate().hasFrame())
		{
			GuiFrame currentFrame = UiCore.locate().getCurrentFrame();
			
			if (!currentFrame.events().contains("dragCanvas"))
			{
				currentFrame.events().set("dragCanvas", new DragCanvas());
			}
			
			DragCanvas canvas = currentFrame.events().get("dragCanvas", DragCanvas.class);
			
			return canvas;
		}
		
		return null;
	}
	
}
