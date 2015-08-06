package com.gildedgames.util.ui.input;


public class KeyboardInput extends ButtonInput
{

	protected int key;

	public KeyboardInput(int key, ButtonState state)
	{
		super(state);

		this.key = key;
	}
	
	public int getKey()
	{
		return this.key;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof KeyboardInput)
		{
			KeyboardInput event = (KeyboardInput)obj;
			
			if (event.getKey() == this.getKey())
			{
				return super.equals(obj);
			}
		}
		
		return false;
	}
	
}
