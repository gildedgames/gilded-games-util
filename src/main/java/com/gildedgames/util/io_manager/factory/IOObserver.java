package com.gildedgames.util.io_manager.factory;

import com.gildedgames.util.io_manager.io.IOFile;

import java.io.File;

public interface IOObserver<I, O>
{

	void preReading(IOFile<I, O> data, File from, I input);
	
}
