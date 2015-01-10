package com.gildedgames.util.io_manager.io;


public interface IO<I, O>
{

	public void write(O output);

	public void read(I input);

}
