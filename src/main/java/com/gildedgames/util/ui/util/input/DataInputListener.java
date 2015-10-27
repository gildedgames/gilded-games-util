package com.gildedgames.util.ui.util.input;

public interface DataInputListener<T>
{

	void onInit();
	
	void onChange(T data);
	
}
