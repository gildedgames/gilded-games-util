package com.gildedgames.util.io_manager.io;

public interface IOFile<I, O> extends IOData<I, O>
{

	Class<?> getDataClass();

	String getFileExtension();

	String getDirectoryName();

}
