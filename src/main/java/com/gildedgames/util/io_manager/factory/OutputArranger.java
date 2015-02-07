package com.gildedgames.util.io_manager.factory;

import java.util.ArrayList;

public interface OutputArranger<O>
{

	O getOutputWrapper();
	
	void rearrange(ArrayList<String> recordedKeys);
	
}
