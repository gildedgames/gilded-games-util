package com.gildedgames.util.modules.ui;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.SidedObject;
import net.minecraftforge.fml.relauncher.Side;

public class UiModule extends Module
{

	public final static UiModule INSTANCE = new UiModule();

	private final SidedObject<UiServices> serviceLocator = new SidedObject<>(new UiServices(Side.CLIENT), new UiServices(Side.SERVER));

	public static UiServices locate()
	{
		return UiModule.INSTANCE.serviceLocator.instance();
	}
}
