package com.gildedgames.util.ui.util.input;

import org.apache.commons.lang3.math.NumberUtils;

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

	@Override
	public boolean validString(String string)
	{
		return NumberUtils.isNumber(string);
	}

	@Override
	public Double parse(String string)
	{
		return Double.parseDouble(string);
	}

}
