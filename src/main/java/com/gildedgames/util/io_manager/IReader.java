package com.gildedgames.util.io_manager;

import java.io.File;

import com.gildedgames.util.io_manager.io.IOFile;

public interface IReader<FILE extends IOFile>
{
	
	public void preReading(FILE data, File from);

}
