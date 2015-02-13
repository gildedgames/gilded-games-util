package com.gildedgames.util.io_manager.factory;

import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;

public interface IOObserver<I, O>
{

	void preReading(IOFile<I, O> data, File from, I input);
	
}
