package com.gildedgames.util.ui.util.input;

public interface DataInput<T>
{

	T getData();

	void setData(T data);

	boolean validString(String string);

	T parse(String string);

}
