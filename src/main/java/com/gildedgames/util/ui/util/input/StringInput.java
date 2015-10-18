package com.gildedgames.util.ui.util.input;


public class StringInput implements DataInput<String>
{
	
	private String data;
	
	public StringInput()
	{
		
	}
	
	public StringInput(String data)
	{
		this.data = data;
	}

	@Override
	public String getData()
	{
		return this.data;
	}

	@Override
	public void setData(String data)
	{
		this.data = data;
	}
	
}
