package com.gildedgames.util.ui.util.input;


public class FloatInput implements DataInput<Float>
{
	
	private float data;
	
	public FloatInput()
	{
		
	}
	
	public FloatInput(float data)
	{
		this.data = data;
	}

	@Override
	public Float getData()
	{
		return this.data;
	}

	@Override
	public void setData(Float data)
	{
		this.data = data;
	}
	
}
