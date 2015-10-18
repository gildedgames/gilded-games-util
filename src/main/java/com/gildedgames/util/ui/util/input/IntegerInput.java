package com.gildedgames.util.ui.util.input;


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
	
}
