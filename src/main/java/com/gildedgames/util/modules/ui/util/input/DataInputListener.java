package com.gildedgames.util.modules.ui.util.input;

public interface DataInputListener<T>
{

	void onInit();
	
	void onChange(T data);
	
}
