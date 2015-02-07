package com.gildedgames.util.io_manager.factory;

import java.util.ArrayList;

public interface InputRecorder<I>
{

	I getInputWrapper();
	
	ArrayList<String> getRecordedKeys();
	
}
