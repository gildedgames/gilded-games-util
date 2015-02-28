package com.gildedgames.util.core.gui;

import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.UIViewer;
import net.minecraft.client.Minecraft;

public class UIViewerMC implements UIViewer
{
	
	public UIViewerMC()
	{
		
	}
	
	@Override
	public void view(UIView view)
	{	
		Minecraft.getMinecraft().displayGuiScreen(view == null ? null : new UIElementWrapperMC(view));
	}
	
}
