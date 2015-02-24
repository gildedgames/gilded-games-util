package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.UIViewer;

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
