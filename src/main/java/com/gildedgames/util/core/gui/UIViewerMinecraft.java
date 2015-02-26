package com.gildedgames.util.core.gui;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.UIViewer;
import net.minecraft.client.Minecraft;

public class UIViewerMinecraft implements UIViewer
{
	
	public UIViewerMinecraft()
	{
		
	}
	
	@Override
	public void view(UIView view)
	{
		Minecraft.getMinecraft().displayGuiScreen(new UIElementWrapperMinecraft(view));
	}
	
}
