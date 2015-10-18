package com.gildedgames.util.ui.util.input;


public class BooleanInput implements DataInput<Boolean>
{
	
	private boolean data;
	
	public BooleanInput()
	{
		
	}
	
	public BooleanInput(boolean data)
	{
		this.data = data;
	}

	@Override
	public Boolean getData()
	{
		return this.data;
	}

	@Override
	public void setData(Boolean data)
	{
		this.data = data;
	}
	
}
