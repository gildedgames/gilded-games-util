package com.gildedgames.util.ui.util.input;


public class DoubleInput implements DataInput<Double>
{
	
	private double data;
	
	public DoubleInput()
	{
		
	}
	
	public DoubleInput(double data)
	{
		this.data = data;
	}

	@Override
	public Double getData()
	{
		return this.data;
	}

	@Override
	public void setData(Double data)
	{
		this.data = data;
	}
	
}
