package com.gildedgames.util.io_manager.io;


public interface IO<I, O>
{

	void write(O output);

	void read(I input);

}
