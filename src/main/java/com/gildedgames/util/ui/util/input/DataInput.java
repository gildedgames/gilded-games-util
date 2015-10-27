package com.gildedgames.util.ui.util.input;

public interface DataInput<T>
{

	T getData();

	void setData(T data);
	
	void addListener(DataInputListener<T> listener);
	
	boolean removeListener(DataInputListener<T> listener);

	boolean validString(String string);

	T parse(String string);

}
