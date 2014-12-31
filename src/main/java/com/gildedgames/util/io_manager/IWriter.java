package com.gildedgames.util.io_manager;

import java.io.DataOutputStream;

import com.gildedgames.util.io_manager.io.IOFile;

public interface IWriter<FILE extends IOFile>
{
	
	public void finishWriting(DataOutputStream input);
	
}
