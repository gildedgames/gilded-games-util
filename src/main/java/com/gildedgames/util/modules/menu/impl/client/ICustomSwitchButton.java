package com.gildedgames.util.modules.menu.impl.client;

public interface ICustomSwitchButton
{

	void render(int mouseX, int mouseY, String buttonText);
	
	boolean isMousedOver(int mouseX, int mouseY);
	
}
