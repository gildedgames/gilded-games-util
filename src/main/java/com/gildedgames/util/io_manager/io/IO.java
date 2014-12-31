package com.gildedgames.util.io_manager.io;

import java.io.IOException;

public interface IO<I, O>
{

	public void write(I output);

	public void read(O input);

}
