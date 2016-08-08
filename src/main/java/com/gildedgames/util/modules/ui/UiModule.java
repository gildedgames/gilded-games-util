package com.gildedgames.util.modules.ui;

import com.gildedgames.util.core.Module;

public class UiModule extends Module
{
	private static final UiServices services = new UiServices();

	public static UiServices locate()
	{
		return services;
	}
}
