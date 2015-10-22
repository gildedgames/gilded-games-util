package com.gildedgames.util.ui.util.input;

import org.apache.commons.lang3.math.NumberUtils;

public class IntegerInput implements DataInput<Integer>
{

	private int data;

	public IntegerInput()
	{

	}

	public IntegerInput(int data)
	{
		this.data = data;
	}

	@Override
	public Integer getData()
	{
		return this.data;
	}

	@Override
	public void setData(Integer data)
	{
		this.data = data;
	}

	@Override
	public boolean validString(String string)
	{
		return NumberUtils.isDigits(string);
	}

	@Override
	public Integer parse(String string)
	{
		return Integer.parseInt(string);
	}

}
