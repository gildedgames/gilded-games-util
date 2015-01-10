package com.gildedgames.util.menuhook.client;

public interface ICustomSwitchButton
{

	void render(int mouseX, int mouseY, String buttonText);
	
	boolean isMousedOver(int mouseX, int mouseY);
	
}
